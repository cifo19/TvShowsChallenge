package com.scene.remote.errorhandler

import com.google.gson.Gson
import com.scene.remote.Constants
import com.scene.remote.errorhandler.moviedb.MovieDbErrorHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandlerFactory @Inject constructor(private val gson: Gson) {

    fun getErrorHandler(url: String): ErrorHandler? {
        val resolvedEndpoint =
            Constants.ServiceEndpoints.values().find { it.endpoint.contains(url) } ?: return null

        return if (resolvedEndpoint == Constants.ServiceEndpoints.MOVIE_SERVICE_BASE_URL) {
            MovieDbErrorHandler(gson)
        } else {
            null
        }
    }
}
