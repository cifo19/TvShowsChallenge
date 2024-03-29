package com.scene.remote.errorhandler.moviedb

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.scene.remote.errorhandler.ErrorHandler
import com.scene.remote.errorhandler.ServiceException
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class MovieDbErrorHandler(private val gson: Gson) : ErrorHandler {

    override fun canHandle(errorCode: Int): Boolean {
        return errorCode == HTTP_UNAUTHORIZED || errorCode == HTTP_NOT_FOUND
    }

    // todo implement an error logger
    @Suppress("SwallowedException")
    override fun handle(responseBody: String?): ServiceException {
        if (responseBody.isNullOrBlank()) return ServiceException()

        val error = try {
            gson.fromJson(responseBody, MovieDbServiceErrorModel::class.java)
        } catch (jsonSyntaxException: JsonSyntaxException) {
            null
        }

        return ServiceException(error?.statusMessage)
    }
}
