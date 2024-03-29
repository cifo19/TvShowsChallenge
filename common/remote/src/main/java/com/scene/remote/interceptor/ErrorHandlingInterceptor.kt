package com.scene.remote.interceptor

import com.scene.remote.errorhandler.ErrorHandlerFactory
import com.scene.remote.errorhandler.ServiceException
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
