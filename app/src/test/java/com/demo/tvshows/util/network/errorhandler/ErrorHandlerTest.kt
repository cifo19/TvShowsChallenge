package com.demo.tvshows.util.network.errorhandler

import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.mock
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ErrorHandlerTest {

    private var gson: Gson = Gson()

    private lateinit var errorHandler: ErrorHandler

    @Before
    fun setUp() {
        errorHandler = ErrorHandler(gson)
    }

    @Test
    fun `When exception is not a HttpException wrap it as ServiceResultException and return`() {
        val throwable = Throwable()

        val parsedException = errorHandler.parseException(throwable)

        assertThat(parsedException).isInstanceOf(ServiceResultException::class.java)
    }

    @Test
    fun `When exception is HttpException but not modeled as expected then return as UnHandledServiceException`() {
        val response: Response<String> = mock()
        val throwable = HttpException(response)

        val parsedException = errorHandler.parseException(throwable)

        assertThat(parsedException).isInstanceOf(UnHandledServiceException::class.java)
    }

    @Test
    fun `When exception is HttpException but statusMessage is null then return as UnHandledServiceException`() {
        val errorModel = ErrorModel(statusMessage = null, statusCode = 0)
        val httpException = createHttpException(errorModel)

        val parsedException = errorHandler.parseException(httpException)

        assertThat(parsedException).isInstanceOf(UnHandledServiceException::class.java)
    }

    @Test
    fun `When exception is HttpException with a valid statusMessage then return as HandledServiceException`() {
        val errorModel = ErrorModel(statusMessage = "Invalid API key: You must be granted a valid key.", statusCode = 7)
        val httpException = createHttpException(errorModel)

        val parsedException = errorHandler.parseException(httpException)

        assertThat(parsedException).isInstanceOf(HandledServiceException::class.java)
    }

    private fun createHttpException(errorModel: ErrorModel, statusCode: Int = 401): HttpException {
        return HttpException(
            Response.error<Any>(
                statusCode,
                ResponseBody.create(MediaType.parse("text/plain"), gson.toJson(errorModel))
            )
        )
    }
}
