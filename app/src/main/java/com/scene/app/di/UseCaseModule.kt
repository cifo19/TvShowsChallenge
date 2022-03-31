package com.scene.app.di

import com.scene.domain.repository.TvShowsRepository
import com.scene.domain.usecase.FetchPopularTvShowsUseCase
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
