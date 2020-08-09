package com.demo.tvshows.errorhandler

import com.demo.tvshows.Constants.ServiceEndpoints
import com.demo.tvshows.errorhandler.moviedb.MovieDbErrorHandler
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandlerFactory @Inject constructor(private val gson: Gson) {

    fun getErrorHandler(url: String): ErrorHandler? {
        val resolvedEndpoint = ServiceEndpoints.values().find { it.endpoint.contains(url) } ?: return null

        return if (resolvedEndpoint == ServiceEndpoints.MOVIE_SERVICE_BASE_URL) {
            MovieDbErrorHandler(gson)
        } else {
            null
        }
    }
}
