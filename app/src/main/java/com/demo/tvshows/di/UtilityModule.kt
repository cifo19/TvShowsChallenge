package com.demo.tvshows.di

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UtilityModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideDefaultGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideConnectivityManager(application: Application): ConnectivityManager {
        return application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}