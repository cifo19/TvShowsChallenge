package com.demo.tvshows.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.tvshows.repository.TvShowsRepository
import com.demo.tvshows.remote.response.TvShowsResponse
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.LoadingAdapterItem
import com.demo.tvshows.util.TestCoroutineRule
import com.demo.tvshows.util.byPausing
import com.demo.tvshows.errorhandler.ServiceException
import com.demo.tvshows.util.parseFile
import com.demo.tvshows.util.runBlocking
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
    private lateinit var tvShowsRepository: TvShowsRepository

    private lateinit var tvShowsViewModel: TvShowsViewModel

    private val tvShowsResponse =
        parseFile<TvShowsResponse>(POPULAR_TV_SHOWS_RESPONSE_PATH)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvShowsViewModel = TvShowsViewModel(tvShowsRepository)
    }

    @Test
    fun `When the next page is not exist then can not load more`() {
        tvShowsViewModel.hasNextPage = false

        val canLoadMore = tvShowsViewModel.canLoadMore

        assertThat(canLoadMore).isFalse()
    }

    @Test
    fun `When the new tv shows are loading then can not load more`() {
        tvShowsViewModel.hasNextPage = true
        tvShowsViewModel._showTvShowsLiveData.value = mutableListOf(LoadingAdapterItem)

        val canLoadMore = tvShowsViewModel.canLoadMore

        assertThat(canLoadMore).isFalse()
    }

    @Test
    fun `When the next page is exist and the new tv shows are not loading then can load more`() {
        tvShowsViewModel.hasNextPage = true
        tvShowsViewModel._showTvShowsLiveData.value = mutableListOf()

        val canLoadMore = tvShowsViewModel.canLoadMore

        assertThat(canLoadMore).isTrue()
    }

    @Test
    fun `When loading more tv show then increase pageIndex`() {
        coEvery { tvShowsRepository.fetchTvShows(any()) } returns tvShowsResponse

        tvShowsViewModel.getTvShows(loadMore = true)

        assertThat(tvShowsViewModel.pageIndex).isEqualTo(2)
    }

    @Test
    fun `When start to fetch tv shows then should show loading`() = testCoroutineRule.runBlocking {
        val tvShowsObserver = mockk<Observer<MutableList<AdapterItem>>>(relaxed = true)
        tvShowsViewModel.showTvShows.observeForever(tvShowsObserver)
        coEvery { tvShowsRepository.fetchTvShows(any()) } returns tvShowsResponse

        testCoroutineRule.testDispatcher.byPausing {
            tvShowsViewModel.getTvShows()
            verify { tvShowsObserver.onChanged(mutableListOf(LoadingAdapterItem)) }
        }
    }

    @Test
    fun `When tv shows are fetched successfully then show tv shows`() = testCoroutineRule.runBlocking {
        val tvShowsObserver = mockk<Observer<MutableList<AdapterItem>>>(relaxed = true)
        tvShowsViewModel.showTvShows.observeForever(tvShowsObserver)
        coEvery { tvShowsRepository.fetchTvShows(any()) } returns tvShowsResponse

        tvShowsViewModel.getTvShows()

        val expectedAdapterItems: MutableList<AdapterItem> = tvShowsResponse.tvShows
            .map(AdapterItem::TvShowAdapterItem)
            .toMutableList()
        verify { tvShowsObserver.onChanged(expectedAdapterItems) }
    }

    @Test
    fun `When tv shows are fetched with failure then show error`() = testCoroutineRule.runBlocking {
        val errorObserver = mockk<Observer<Throwable>>(relaxed = true)
        tvShowsViewModel.onError.observeForever(errorObserver)
        coEvery { tvShowsRepository.fetchTvShows(any()) } answers { throw ServiceException() }

        tvShowsViewModel.getTvShows()

        verify { errorObserver.onChanged(ServiceException()) }
    }

    companion object {
        private const val POPULAR_TV_SHOWS_RESPONSE_PATH = "get_popular_tv_shows_success.json"
    }
}
