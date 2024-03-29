package com.scene.homedata.remote

import com.scene.homedata.remote.service.MovieDbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRemoteDataModule {

    @Provides
    @Singleton
    fun provideMovieDatabaseService(retrofit: Retrofit): MovieDbService {
        return retrofit.create(MovieDbService::class.java)
    }
}
