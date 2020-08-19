package com.demo.tvshows.repository

import com.demo.tvshows.util.parseFile
import com.demo.tvshows.remote.MovieDatabaseService
import com.demo.tvshows.remote.response.TvShowsResponse
import com.demo.tvshows.errorhandler.ServiceException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class TvShowsRepositoryTest {

    @MockK
    private lateinit var movieDatabaseService: MovieDatabaseService
    private lateinit var tvShowsRepository: TvShowsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvShowsRepository = TvShowsRepository(movieDatabaseService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Whenever request is succeeded then return response`() = runBlockingTest {
        val expectedResponse =
            parseFile<TvShowsResponse>(POPULAR_TV_SHOWS_RESPONSE_PATH)
        coEvery { movieDatabaseService.getPopularTvShows(any()) } returns expectedResponse

        val actualResponse = tvShowsRepository.fetchTvShows(pageIndex = 1)

        coVerify { movieDatabaseService.getPopularTvShows(any()) }
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = ServiceException::class)
    fun `Whenever request is failed then throw exception`() = runBlockingTest {
        coEvery { movieDatabaseService.getPopularTvShows(any()) } coAnswers { throw ServiceException() }

        tvShowsRepository.fetchTvShows(pageIndex = 1)
    }

    companion object {
        private const val POPULAR_TV_SHOWS_RESPONSE_PATH = "get_popular_tv_shows_success.json"
    }
}
