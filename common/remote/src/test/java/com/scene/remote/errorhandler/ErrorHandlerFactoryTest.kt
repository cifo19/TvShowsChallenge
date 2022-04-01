package com.scene.remote.errorhandler

import com.google.gson.Gson
import com.scene.remote.errorhandler.moviedb.MovieDbErrorHandler
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
    fun `Return null when the url is undefined`() {
        val actualErrorHandler = errorHandlerFactory.getErrorHandler("undefinedUrl")

        assertThat(actualErrorHandler).isNull()
    }

    @Test
    fun `Return relative errorHandler when the url is defined`() {
        val actualErrorHandler = errorHandlerFactory.getErrorHandler("https://api.themoviedb.org/3/")

        assertThat(actualErrorHandler).isInstanceOf(MovieDbErrorHandler::class.java)
    }
}
