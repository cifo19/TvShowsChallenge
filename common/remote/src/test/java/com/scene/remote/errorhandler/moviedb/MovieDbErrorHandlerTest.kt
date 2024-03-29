package com.scene.remote.errorhandler.moviedb

import com.google.gson.Gson
import com.scene.remote.errorhandler.ServiceException
import com.scene.remote.util.parseFile
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
    fun `Handle error when the code is HTTP_UNAUTHORIZED`() {
        val canHandle = movieDbErrorHandler.canHandle(HTTP_UNAUTHORIZED)

        assertThat(canHandle).isTrue
    }

    @Test
    fun `Handle error when the code is HTTP_NOT_FOUND`() {
        val canHandle = movieDbErrorHandler.canHandle(HTTP_NOT_FOUND)

        assertThat(canHandle).isTrue
    }

    @Test
    fun `Does not handle error when the code is unknown`() {
        val canHandle = movieDbErrorHandler.canHandle(1)

        assertThat(canHandle).isFalse
    }

    @Test
    fun `Return ServiceException when the response body is null`() {
        val exception = movieDbErrorHandler.handle(null)

        assertThat(exception).isInstanceOf(ServiceException::class.java)
    }

    @Test
    fun `Return ServiceException with message when the response body is in expected format`() {
        val error = parseFile<MovieDbServiceErrorModel>("get_popular_tv_shows_error.json")

        val exception = movieDbErrorHandler.handle(gson.toJson(error))

        val expectedError = ServiceException(error.statusMessage)
        assertThat(exception).isEqualTo(expectedError)
    }

    @Test
    fun `Return ServiceException with no message when the response body is in unexpected format`() {
        val exception = movieDbErrorHandler.handle("unexpected response body")

        assertThat(exception).isEqualTo(ServiceException())
    }
}
