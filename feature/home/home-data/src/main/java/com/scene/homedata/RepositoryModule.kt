package com.scene.homedata

import com.scene.homedata.repository.TvShowsRepositoryImpl
import com.scene.homedomain.repository.TvShowsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsTvShowsRepository(tvShowsRepositoryImpl: TvShowsRepositoryImpl): TvShowsRepository
}
