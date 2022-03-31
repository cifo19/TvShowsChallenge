package com.scene.app.di

import com.scene.domain.repository.TvShowsRepository
import com.scene.data.repository.TvShowsRepositoryImpl
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
