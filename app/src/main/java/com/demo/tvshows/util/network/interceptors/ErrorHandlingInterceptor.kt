package com.demo.tvshows.util.network.interceptors

import com.demo.tvshows.errorhandler.ErrorHandlerFactory
import com.demo.tvshows.errorhandler.ServiceException
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandlingInterceptor @Inject constructor(
    private val errorHandlerFactory: ErrorHandlerFactory
) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.isSuccessful) return response

        val url = chain.request().url.host
        val errorHandler = errorHandlerFactory.getErrorHandler(url) ?: throw ServiceException()

        throw if (errorHandler.canHandle(response.code)) {
            errorHandler.handle(response.body?.string())
        } else {
            ServiceException()
        }
    }
}
