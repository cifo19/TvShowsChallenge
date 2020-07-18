package com.demo.tvshows.util.network.errorhandler

import com.demo.tvshows.util.network.errorhandler.moviedb.MovieDbErrorHandler
import com.google.gson.Gson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ErrorHandlerFactoryTest {

    private var gson: Gson = Gson()

    private lateinit var errorHandlerFactory: ErrorHandlerFactory

    @Before
    fun setUp() {
        errorHandlerFactory = ErrorHandlerFactory(gson)
    }

    @Test
    fun `When the url is undefined then should return null`() {
        val actualErrorHandler = errorHandlerFactory.getErrorHandler("undefinedUrl")

        assertThat(actualErrorHandler).isNull()
    }

    @Test
    fun `When the url is defined then should return relative errorHandler`() {
        val actualErrorHandler = errorHandlerFactory.getErrorHandler("https://api.themoviedb.org/3/")

        assertThat(actualErrorHandler).isInstanceOf(MovieDbErrorHandler::class.java)
    }
}
