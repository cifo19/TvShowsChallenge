package com.demo.tvshows.di

import com.demo.tvshows.repository.TvShowsRepository
import com.demo.tvshows.usecase.FetchPopularTvShowsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {

    @Provides
    fun provideFetchPopularTvShowsUseCase(tvShowsRepository: TvShowsRepository): FetchPopularTvShowsUseCase {
        return FetchPopularTvShowsUseCase(tvShowsRepository)
    }
}
