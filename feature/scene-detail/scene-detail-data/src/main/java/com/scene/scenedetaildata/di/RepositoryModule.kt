package com.scene.scenedetaildata.di

import com.scene.scenedetaildata.repository.TvShowDetailRepositoryImpl
import com.scene.scenedetaildomain.repository.TvShowDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsTvShowsRepository(
        tvShowsRepositoryImpl: TvShowDetailRepositoryImpl
    ): TvShowDetailRepository
}
