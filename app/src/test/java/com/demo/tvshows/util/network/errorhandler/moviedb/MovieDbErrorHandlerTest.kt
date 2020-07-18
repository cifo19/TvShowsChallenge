package com.demo.tvshows.util.network.errorhandler.moviedb

import com.demo.tvshows.util.parseFile
import com.demo.tvshows.util.network.errorhandler.ServiceException
import com.google.gson.Gson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class MovieDbErrorHandlerTest {

    private val gson = Gson()

    private lateinit var movieDbErrorHandler: MovieDbErrorHandler

    @Before
    fun setUp() {
        movieDbErrorHandler = MovieDbErrorHandler(gson)
    }

    @Test
    fun `When the error code is HTTP_UNAUTHORIZED then should handle`() {
        val canHandle = movieDbErrorHandler.canHandle(HTTP_UNAUTHORIZED)

        assertThat(canHandle).isTrue()
    }

    @Test
    fun `When the error code is HTTP_NOT_FOUND then should handle`() {
        val canHandle = movieDbErrorHandler.canHandle(HTTP_NOT_FOUND)

        assertThat(canHandle).isTrue()
    }

    @Test
    fun `When the error code is unknown then should not handle`() {
        val canHandle = movieDbErrorHandler.canHandle(1)

        assertThat(canHandle).isFalse()
    }

    @Test
    fun `When the response body is null then should return ServiceException`() {
        val exception = movieDbErrorHandler.handle(null)

        assertThat(exception).isInstanceOf(ServiceException::class.java)
    }

    @Test
    fun `When the response body is in expected format then should return ServiceException with message`() {
        val error =
            parseFile<MovieDbServiceErrorModel>("get_popular_tv_shows_error.json")

        val exception = movieDbErrorHandler.handle(gson.toJson(error))

        val expectedError = ServiceException(error.statusMessage)
        assertThat(exception).isEqualTo(expectedError)
    }

    @Test
    fun `When the response body is in unexpected format then should return ServiceException with no message`() {
        val exception = movieDbErrorHandler.handle("unexpected response body")

        assertThat(exception).isEqualTo(ServiceException())
    }
}