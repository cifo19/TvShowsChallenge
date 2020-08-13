package com.demo.tvshows.di

import com.demo.tvshows.repository.TvShowsRepository
import com.demo.tvshows.repository.TvShowsRepositoryImpl
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
