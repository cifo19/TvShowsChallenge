package com.demo.tvshows.util.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class DefaultParameterInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        val paramsAddedHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(KEY_API_KEY, VALUE_API_KEY)
            .addQueryParameter(KEY_LANGUAGE, VALUE_LANGUAGE)
            .build()

        val eventualRequest = originalRequest.newBuilder()
            .url(paramsAddedHttpUrl)
            .build()

        return chain.proceed(eventualRequest)
    }

    companion object {
        private const val KEY_API_KEY = "api_key"
        private const val KEY_LANGUAGE = "language"
        private const val VALUE_API_KEY = "5d967c7c335764f39b1efbe9c5de9760"
        private const val VALUE_LANGUAGE = "en_US"
    }
}
