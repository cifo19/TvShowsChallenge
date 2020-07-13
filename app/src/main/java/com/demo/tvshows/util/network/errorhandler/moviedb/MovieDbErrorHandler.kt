package com.demo.tvshows.util.network.errorhandler.moviedb

import android.util.Log
import com.demo.tvshows.util.network.errorhandler.ErrorHandler
import com.demo.tvshows.util.network.errorhandler.ServiceException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class MovieDbErrorHandler(private val gson: Gson) : ErrorHandler {

    override fun handle(responseBody: String?): ServiceException {
        if (responseBody.isNullOrBlank()) return ServiceException()

        val error = try {
            gson.fromJson(responseBody, MovieDbServiceErrorModel::class.java)
        } catch (jsonSyntaxException: JsonSyntaxException) {
            Log.d("exception", jsonSyntaxException.message)
            null
        }

        return ServiceException(error?.statusMessage)
    }

    override fun canHandle(errorCode: Int): Boolean {
        return errorCode == HTTP_UNAUTHORIZED || errorCode == HTTP_NOT_FOUND
    }
}
