package com.demo.tvshows.util.network

import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class InternetConnectivityInterceptor @Inject constructor(
    private val connectivityManager: ConnectivityManager
): Interceptor{
    override fun intercept(chain: Chain): Response {
        val noConnection = connectivityManager.activeNetworkInfo?.isConnected != true
        if (noConnection) {
            throw NoConnectionException(chain.request().url().toString())
        }

        return chain.proceed(chain.request())
    }
}