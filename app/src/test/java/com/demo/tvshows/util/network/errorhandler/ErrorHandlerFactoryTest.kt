package com.demo.tvshows.util.network.errorhandler

import com.demo.tvshows.util.network.errorhandler.moviedb.MovieDbServiceErrorModel
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
class ErrorHandlerFactoryTest {

    private var gson: Gson = Gson()

    private lateinit var errorHandlerFactory: ErrorHandlerFactory

    @Before
    fun setUp() {
        errorHandlerFactory = ErrorHandlerFactory(gson)
    }

    @Test
    fun `When exception is not a HttpException wrap it as ServiceResultException and return`() {
        val throwable = Throwable()

        val parsedException = errorHandlerFactory.parseException(throwable)

        assertThat(parsedException).isInstanceOf(ServiceException::class.java)
    }

    @Test
    fun `When exception is HttpException but not modeled as expected then return as UnHandledServiceException`() {
        val response: Response<String> = mock()
        val throwable = HttpException(response)

        val parsedException = errorHandlerFactory.parseException(throwable)

        assertThat(parsedException).isInstanceOf(UnHandledServiceException::class.java)
    }

    @Test
    fun `When exception is HttpException but statusMessage is null then return as UnHandledServiceException`() {
        val errorModel = MovieDbServiceErrorModel(
            statusMessage = null,
            statusCode = 0
        )
        val httpException = createHttpException(errorModel)

        val parsedException = errorHandlerFactory.parseException(httpException)

        assertThat(parsedException).isInstanceOf(UnHandledServiceException::class.java)
    }

    @Test
    fun `When exception is HttpException with a valid statusMessage then return as HandledServiceException`() {
        val errorModel = MovieDbServiceErrorModel(
            statusMessage = "Invalid API key: You must be granted a valid key.",
            statusCode = 7
        )
        val httpException = createHttpException(errorModel)

        val parsedException = errorHandlerFactory.parseException(httpException)

        assertThat(parsedException).isInstanceOf(HandledServiceException::class.java)
    }

    private fun createHttpException(movieDbServiceErrorModel: MovieDbServiceErrorModel, statusCode: Int = 401): HttpException {
        return HttpException(
            Response.error<Any>(
                statusCode,
                ResponseBody.create(MediaType.parse("text/plain"), gson.toJson(movieDbServiceErrorModel))
            )
        )
    }
}
