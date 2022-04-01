package com.scene.homedata

import com.scene.homedata.repository.TvShowsRepositoryImpl
import com.scene.homedomain.repository.TvShowsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsTvShowsRepository(tvShowsRepositoryImpl: TvShowsRepositoryImpl): TvShowsRepository
}
