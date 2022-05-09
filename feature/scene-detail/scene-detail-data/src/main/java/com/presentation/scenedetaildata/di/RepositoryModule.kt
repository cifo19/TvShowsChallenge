package com.presentation.scenedetaildata.di

import com.presentation.scenedetaildata.repository.TvShowDetailRepositoryImpl
import com.presentation.scenedetaildomain.repository.TvShowDetailRepository
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
