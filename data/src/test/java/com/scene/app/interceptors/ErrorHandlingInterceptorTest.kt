package com.scene.app.interceptors

import com.scene.data.errorhandler.ErrorHandlerFactory
import com.scene.data.errorhandler.ServiceException
import com.scene.data.errorhandler.moviedb.MovieDbErrorHandler
import com.scene.data.errorhandler.moviedb.MovieDbServiceErrorModel
import com.scene.remote.response.TvShowsResponse
import com.scene.app.util.parseFile
import com.google.gson.Gson
import com.scene.data.interceptors.ErrorHandlingInterceptor
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_NOT_FOUND

class ErrorHandlingInterceptorTest {

    @MockK
    private lateinit var errorHandlerFactor: ErrorHandlerFactory

    private lateinit var errorHandlingInterceptor: ErrorHandlingInterceptor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        errorHandlingInterceptor = ErrorHandlingInterceptor(errorHandlerFactor)
    }

    @Test
    fun `Proceed response when response is succeeded`() {
        val popularShowsSuccessResponse = parseFile<TvShowsResponse>("get_popular_tv_shows_success.json")
        val mockWebServer = MockWebServer()
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(errorHandlingInterceptor).build()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse().apply { setBody(Gson().toJson(popularShowsSuccessResponse)) })

        val response = okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

        assertThat(response.isSuccessful).isTrue
        assertThat(response.body!!.string()).isEqualTo(Gson().toJson(popularShowsSuccessResponse))
        mockWebServer.shutdown()
    }

    @Test(expected = ServiceException::class)
    fun `Throw ServiceException when the error is not handled`() {
        val mockWebServer = MockWebServer()
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(errorHandlingInterceptor).build()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse().apply { setResponseCode(HTTP_BAD_REQUEST) })
        every { errorHandlerFactor.getErrorHandler(any()) }.returns(MovieDbErrorHandler(Gson()))

        okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

        mockWebServer.shutdown()
    }

    @Test
    fun `Throw ServiceException with relative message when the error is handled`() {
        val popularShowsErrorResponse = parseFile<MovieDbServiceErrorModel>("get_popular_tv_shows_error.json")
        val mockWebServer = MockWebServer()
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(errorHandlingInterceptor).build()
        mockWebServer.start()
        mockWebServer.enqueue(
            MockResponse()
                .apply { setResponseCode(HTTP_NOT_FOUND) }
                .apply { setBody(Gson().toJson(popularShowsErrorResponse)) }
        )
        every { errorHandlerFactor.getErrorHandler(any()) }.returns(MovieDbErrorHandler(Gson()))

        val throwable = catchThrowable {
            okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()
        }

        assertThat(throwable).isInstanceOf(ServiceException::class.java)
        assertThat(throwable.message).isEqualTo(popularShowsErrorResponse.statusMessage)
        mockWebServer.shutdown()
    }
}
