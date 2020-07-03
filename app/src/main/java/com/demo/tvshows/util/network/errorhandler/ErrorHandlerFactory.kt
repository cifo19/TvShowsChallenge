package com.demo.tvshows.util.network.errorhandler

import com.demo.tvshows.data.Constants.ServiceEndpoints
import com.demo.tvshows.util.network.errorhandler.moviedb.MovieDbErrorHandler
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
