package com.scene.homepresentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.scene.homedomain.entity.TvShowEntity
import com.scene.homedomain.entity.TvShowsResponseEntity
import com.scene.homedomain.usecase.FetchPopularTvShowsUseCase
import com.scene.homepresentation.adapteritem.LoadingAdapterItem
import com.scene.homepresentation.adapteritem.TvShowAdapterItem
import com.scene.homepresentation.mapper.TvShowAdapterItemMapper
import com.scene.homepresentation.util.TestCoroutineRule
import com.scene.homepresentation.util.byPausing
import com.scene.homepresentation.util.runBlocking
import com.scene.util.AdapterItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TvShowsViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var fetchPopularTvShowsUseCase: FetchPopularTvShowsUseCase

    private lateinit var tvShowsViewModel: TvShowsViewModel

    private val tvShowAdapterItemMapper = TvShowAdapterItemMapper()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvShowsViewModel = TvShowsViewModel(
            fetchPopularTvShowsUseCase,
            tvShowAdapterItemMapper
        )
    }

    @Test
    fun `Could not load more when the next page is not exist`() {
        tvShowsViewModel.hasNextPage = false

        val canLoadMore = tvShowsViewModel.canLoadMore

        assertThat(canLoadMore).isFalse
    }

    @Test
    fun `Could not load more when the new tv shows are loading`() {
        tvShowsViewModel.hasNextPage = true
        tvShowsViewModel._showTvShowsLiveData.value = mutableListOf(LoadingAdapterItem)

        val canLoadMore = tvShowsViewModel.canLoadMore

        assertThat(canLoadMore).isFalse
    }

    @Test
    fun `Could load more when the next page is exist and the new tv shows are not loading`() {
        tvShowsViewModel.hasNextPage = true
        tvShowsViewModel._showTvShowsLiveData.value = mutableListOf()

        val canLoadMore = tvShowsViewModel.canLoadMore

        assertThat(canLoadMore).isTrue
    }

    @Test
    fun `Increase pageIndex when loading more tv show`() {
        coEvery { fetchPopularTvShowsUseCase(any()) } returns getDummyTvShowsResponseEntity()

        tvShowsViewModel.getTvShows(loadMore = true)

        assertThat(tvShowsViewModel.pageIndex).isEqualTo(2)
    }

    @Test
    fun `Show loading when starts to fetch tv shows`() = testCoroutineRule.runBlocking {
        val tvShowsObserver = mockk<Observer<MutableList<com.scene.util.AdapterItem>>>(relaxed = true)
        tvShowsViewModel.showTvShows.observeForever(tvShowsObserver)
        coEvery { fetchPopularTvShowsUseCase(any()) } returns getDummyTvShowsResponseEntity()

        testCoroutineRule.testDispatcher.byPausing {
            tvShowsViewModel.getTvShows()
            verify { tvShowsObserver.onChanged(mutableListOf(LoadingAdapterItem)) }
        }
    }

    @Test
    fun `Show tv shows when tv shows are fetched successfully`() = testCoroutineRule.runBlocking {
        val tvShowsResponseEntity = getDummyTvShowsResponseEntity()
        val tvShowsObserver = mockk<Observer<MutableList<AdapterItem>>>(relaxed = true)
        tvShowsViewModel.showTvShows.observeForever(tvShowsObserver)
        coEvery { fetchPopularTvShowsUseCase(any()) } returns tvShowsResponseEntity

        tvShowsViewModel.getTvShows()

        val expectedAdapterItems: MutableList<AdapterItem> = tvShowsResponseEntity.tvShowEntities
            .map {
                TvShowAdapterItem(
                    it.id,
                    it.name,
                    it.overview,
                    it.posterPath,
                    it.voteAverage.toString()
                )
            }
            .toMutableList()
        verify { tvShowsObserver.onChanged(expectedAdapterItems) }
    }

    @Test
    fun `Show error when tv shows are fetched with failure`() = testCoroutineRule.runBlocking {
        val errorObserver = mockk<Observer<Throwable>>(relaxed = true)
        val exception = IllegalStateException()
        tvShowsViewModel.onError.observeForever(errorObserver)
        coEvery { fetchPopularTvShowsUseCase(any()) } answers { throw exception }

        tvShowsViewModel.getTvShows()

        verify { errorObserver.onChanged(exception) }
    }

    private fun getDummyTvShowsResponseEntity(): TvShowsResponseEntity {
        val tvShowEntities = listOf(
            TvShowEntity(
                backdropPath = "backdropPath",
                firstAirDate = "firstAirDate",
                genreIds = emptyList(),
                id = 1,
                name = "name",
                originCountry = emptyList(),
                originalLanguage = "originalLanguage",
                originalName = "originalName",
                overview = "overview",
                popularity = 0.0,
                posterPath = "posterPath",
                voteAverage = 0.0,
                voteCount = 0
            )
        )
        return TvShowsResponseEntity(page = 1, tvShowEntities = tvShowEntities, totalPages = 0, totalResults = 0)
    }
}
