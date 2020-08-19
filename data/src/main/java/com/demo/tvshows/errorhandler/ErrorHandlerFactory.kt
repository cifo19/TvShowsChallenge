package com.demo.tvshows.errorhandler

import android.content.Context
import com.demo.tvshows.Constants.ServiceEndpoints
import com.demo.tvshows.errorhandler.moviedb.MovieDbErrorHandler
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandlerFactory @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    fun getErrorHandler(url: String): ErrorHandler? {
        context.toString()
        val resolvedEndpoint = ServiceEndpoints.values().find { it.endpoint.contains(url) } ?: return null

        return if (resolvedEndpoint == ServiceEndpoints.MOVIE_SERVICE_BASE_URL) {
            MovieDbErrorHandler(gson)
        } else {
            null
        }
    }
}
