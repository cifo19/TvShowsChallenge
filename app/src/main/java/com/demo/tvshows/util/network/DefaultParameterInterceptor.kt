package com.demo.tvshows.util.network

import com.demo.tvshows.BuildConfig
import com.demo.tvshows.BuildConfig.API_KEY
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class DefaultParameterInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        val paramsAddedHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(KEY_API_KEY, API_KEY)
            .addQueryParameter(KEY_LANGUAGE_CODE, BuildConfig.API_LANGUAGE_CODE)
            .build()

        val eventualRequest = originalRequest.newBuilder()
            .url(paramsAddedHttpUrl)
            .build()

        return chain.proceed(eventualRequest)
    }

    companion object {
        const val KEY_API_KEY = "api_key"
        const val KEY_LANGUAGE_CODE = "language"
    }
}
