package com.scene.remote

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteModule {

    @Provides
    @Singleton
    fun providePicasso(@ApplicationContext context: Context, okHttpDownloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
            .loggingEnabled(BuildConfig.DEBUG)
            .downloader(okHttpDownloader)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttpDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideDefaultGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(application: Application): ConnectivityManager {
        return application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
