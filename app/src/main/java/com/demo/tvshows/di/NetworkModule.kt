package com.demo.tvshows.di

import com.demo.tvshows.BuildConfig
import com.demo.tvshows.data.BaseUrls.MOVIE_SERVICE_BASE_URL
import com.demo.tvshows.util.network.DefaultParameterInterceptor
import com.demo.tvshows.util.network.InternetConnectivityInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(MOVIE_SERVICE_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        defaultParameterInterceptor: DefaultParameterInterceptor,
        internetConnectivityInterceptor: InternetConnectivityInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(OK_HTTP_CONNECTION_TIME_OUT, MILLISECONDS)
            .readTimeout(OK_HTTP_READ_TIME_OUT, MILLISECONDS)
            .writeTimeout(OK_HTTP_WRITE_TIME_OUT, MILLISECONDS)
            .addInterceptor(internetConnectivityInterceptor)
            .addInterceptor(defaultParameterInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    companion object {
        private const val OK_HTTP_CONNECTION_TIME_OUT = 150000L
        private const val OK_HTTP_READ_TIME_OUT = 150000L
        private const val OK_HTTP_WRITE_TIME_OUT = 150000L
    }
}
