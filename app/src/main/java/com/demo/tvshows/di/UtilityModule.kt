package com.demo.tvshows.di

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.demo.tvshows.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
object UtilityModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providePicasso(context: Context, okHttpDownloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
            .loggingEnabled(BuildConfig.DEBUG)
            .downloader(okHttpDownloader)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkhttpDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

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
