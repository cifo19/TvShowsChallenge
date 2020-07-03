package com.demo.tvshows.data.model

import com.demo.tvshows.data.remote.MovieDatabaseService
import com.demo.tvshows.data.remote.response.TvShowsResponse
import com.demo.tvshows.helper.TestUtils
import com.demo.tvshows.helper.parseFile
import com.demo.tvshows.util.network.errorhandler.UnHandledServiceException
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsModelTest {

    @Mock
    private lateinit var movieDatabaseService: MovieDatabaseService
    private lateinit var tvShowsModel: TvShowsModel

    private val errorHandler: ErrorHandler = ErrorHandler(Gson())

    @Before
    fun setUp() {
        TestUtils.hookTestRxJavaSchedulers()
        tvShowsModel = TvShowsModel(movieDatabaseService, errorHandler)
    }

    @Test
    fun `Whenever request is succeeded then return response`() {
        val response = parseFile<TvShowsResponse>(POPULAR_TV_SHOWS_RESPONSE_PATH)
        whenever(movieDatabaseService.getPopularTvShows(1)).thenReturn(Single.just(response))

        val testObserver = tvShowsModel.fetchTvShows().test()

        verify(movieDatabaseService).getPopularTvShows(1)
        with(testObserver) {
            assertSubscribed()
            assertNoErrors()
            assertComplete()
        }
    }

    @Test
    fun `Whenever request is failed then throw exception`() {
        val throwable = Throwable()
        whenever(movieDatabaseService.getPopularTvShows(1)).thenReturn(Single.error(throwable))

        val testObserver = tvShowsModel.fetchTvShows().test()

        verify(movieDatabaseService).getPopularTvShows(1)
        with(testObserver) {
            assertSubscribed()
            assertFailure(UnHandledServiceException::class.java)
        }
    }

    companion object {
        private const val POPULAR_TV_SHOWS_RESPONSE_PATH = "get_popular_tv_shows_success.json"
    }
}
