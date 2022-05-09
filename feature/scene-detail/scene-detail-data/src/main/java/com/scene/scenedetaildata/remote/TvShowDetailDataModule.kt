package com.scene.scenedetaildata.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object TvShowDetailDataModule {

    @Provides
    @Singleton
    fun provideTvShowDetailService(retrofit: Retrofit): TvShowDetailService {
        return retrofit.create(TvShowDetailService::class.java)
    }
}
