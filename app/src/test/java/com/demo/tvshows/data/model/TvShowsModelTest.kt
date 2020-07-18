package com.demo.tvshows.data.model

import com.demo.tvshows.data.remote.MovieDatabaseService
import com.demo.tvshows.data.remote.response.TvShowsResponse
import com.demo.tvshows.util.parseFile
import com.demo.tvshows.util.network.errorhandler.ServiceException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class TvShowsModelTest {

    @MockK
    private lateinit var movieDatabaseService: MovieDatabaseService
    private lateinit var tvShowsModel: TvShowsModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvShowsModel = TvShowsModel(movieDatabaseService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Whenever request is succeeded then return response`() = runBlockingTest {
        val expectedResponse =
            parseFile<TvShowsResponse>(POPULAR_TV_SHOWS_RESPONSE_PATH)
        coEvery { movieDatabaseService.getPopularTvShows(any()) } returns expectedResponse

        val actualResponse = tvShowsModel.fetchTvShows(pageIndex = 1)

        coVerify { movieDatabaseService.getPopularTvShows(any()) }
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = ServiceException::class)
    fun `Whenever request is failed then throw exception`() = runBlockingTest {
        coEvery { movieDatabaseService.getPopularTvShows(any()) } coAnswers { throw ServiceException() }

        tvShowsModel.fetchTvShows(pageIndex = 1)
    }

    companion object {
        private const val POPULAR_TV_SHOWS_RESPONSE_PATH = "get_popular_tv_shows_success.json"
    }
}
