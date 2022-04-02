package com.scene.remote.interceptor

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.scene.remote.errorhandler.NoConnectionException
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetConnectivityInterceptor @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : Interceptor {

    @Synchronized
    override fun intercept(chain: Chain): Response {
        val networkCapabilities =
            connectivityManager.activeNetwork ?: throw NoConnectionException(chain.request().url.toString())
        val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities)
            ?: throw NoConnectionException(chain.request().url.toString())
        if (isNetworkHasInternetCapability(activeNetwork).not()) {
            throw NoConnectionException(chain.request().url.toString())
        }

        return chain.proceed(chain.request())
    }

    private fun isNetworkHasInternetCapability(network: NetworkCapabilities): Boolean {
        return network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
