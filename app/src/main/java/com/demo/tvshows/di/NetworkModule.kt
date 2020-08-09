package com.demo.tvshows.di

import com.demo.tvshows.BuildConfig
import com.demo.tvshows.Constants.ServiceEndpoints
import com.demo.tvshows.remote.MovieDatabaseService
import com.demo.tvshows.util.network.interceptors.DefaultParameterInterceptor
import com.demo.tvshows.util.network.interceptors.ErrorHandlingInterceptor
import com.demo.tvshows.util.network.interceptors.InternetConnectivityInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    private const val OK_HTTP_CONNECTION_TIME_OUT = 150000L
    private const val OK_HTTP_READ_TIME_OUT = 150000L
    private const val OK_HTTP_WRITE_TIME_OUT = 150000L

    @Provides
    @Singleton
    fun provideMovieDatabaseService(retrofit: Retrofit): MovieDatabaseService {
        return retrofit.create(MovieDatabaseService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(ServiceEndpoints.MOVIE_SERVICE_BASE_URL.endpoint)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        defaultParameterInterceptor: DefaultParameterInterceptor,
        internetConnectivityInterceptor: InternetConnectivityInterceptor,
        errorHandlingInterceptor: ErrorHandlingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(OK_HTTP_CONNECTION_TIME_OUT, MILLISECONDS)
            .readTimeout(OK_HTTP_READ_TIME_OUT, MILLISECONDS)
            .writeTimeout(OK_HTTP_WRITE_TIME_OUT, MILLISECONDS)
            .addInterceptor(internetConnectivityInterceptor)
            .addInterceptor(defaultParameterInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorHandlingInterceptor)
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

    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}
