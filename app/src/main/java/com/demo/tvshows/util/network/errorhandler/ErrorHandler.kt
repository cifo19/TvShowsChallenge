package com.demo.tvshows.util.network.errorhandler

import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor(private val gson: Gson) {

    fun parseException(throwable: Throwable): ServiceResultException {
        val errorBody = (throwable as? HttpException)?.response()?.errorBody()?.string()
        errorBody ?: return throwable.wrapToServiceResult()

        val statusMessage = gson.fromJson<ErrorModel>(errorBody, ErrorModel::class.java).statusMessage
            ?: return UnHandledServiceException(throwable)

        return HandledServiceException(statusMessage)
    }

    private fun Throwable.wrapToServiceResult(): ServiceResultException {
        if (this is ServiceResultException) return this

        return UnHandledServiceException(this)
    }
}
