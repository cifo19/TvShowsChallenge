package com.demo.tvshows.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.tvshows.data.model.TvShowsModel
import com.demo.tvshows.data.remote.response.TvShowsResponse
import com.demo.tvshows.helper.TestUtils
import com.demo.tvshows.helper.parseFile
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.TvShowAdapterItem
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowsModel: TvShowsModel

    private lateinit var tvShowsViewModel: TvShowsViewModel

    private val tvShowsResponse = parseFile<TvShowsResponse>(POPULAR_TV_SHOWS_RESPONSE_PATH)

    @Before
    fun setUp() {
        TestUtils.hookTestRxJavaSchedulers()
        tvShowsViewModel = TvShowsViewModel(tvShowsModel)
    }

    @Test
    fun `When tv shows are fetching then do not request again`() {
        tvShowsViewModel.fetchingTvShows = true

        tvShowsViewModel.getTvShows()

        verifyZeroInteractions(tvShowsModel)
    }

    @Test
    fun `When tv shows intended to fetch for load more then increment index count`() {
        val expectedPageIndex = 2
        whenever(tvShowsModel.fetchTvShows(expectedPageIndex)).thenReturn(Single.just(tvShowsResponse))

        tvShowsViewModel.getTvShows(loadMore = true)

        verify(tvShowsModel).fetchTvShows(expectedPageIndex)
        assertThat(tvShowsViewModel.pageIndex).isEqualTo(expectedPageIndex)
    }

    @Test
    fun `When before fetching tv shows should show loading`() {
        whenever(tvShowsModel.fetchTvShows()).thenReturn(Single.just(tvShowsResponse))

        tvShowsViewModel.getTvShows()

        verify(tvShowsModel).fetchTvShows()
        assertThat(tvShowsViewModel.toggleListLoading.value).isTrue()
    }

    @Test
    fun `When request is succeeded update hasNextPage and show tv shows()`() {
        whenever(tvShowsModel.fetchTvShows()).thenReturn(Single.just(tvShowsResponse))

        tvShowsViewModel.getTvShows()

        val expectedHasNextPage = tvShowsResponse.page != tvShowsResponse.totalPages
        val expectedAdapterItems = tvShowsResponse.tvShows.map { TvShowAdapterItem(it) }
        verify(tvShowsModel).fetchTvShows()
        assertThat(tvShowsViewModel.hasNextPage).isEqualTo(expectedHasNextPage)
        assertThat(tvShowsViewModel.showTvShows.value).isEqualTo(expectedAdapterItems)
    }

    @Test
    fun `When request is failed then hide loading and show alert`() {
        val throwable = Throwable()
        whenever(tvShowsModel.fetchTvShows()).thenReturn(Single.error(throwable))

        tvShowsViewModel.getTvShows()

        verify(tvShowsModel).fetchTvShows()

        assertThat(tvShowsViewModel.onError.value).isEqualTo(throwable)
        assertThat(tvShowsViewModel.toggleListLoading.value).isFalse()
    }

    companion object {
        private const val POPULAR_TV_SHOWS_RESPONSE_PATH = "get_popular_tv_shows_success.json"
    }
}