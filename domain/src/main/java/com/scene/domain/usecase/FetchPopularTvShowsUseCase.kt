package com.scene.domain.usecase

import com.scene.domain.entity.TvShowsResponseEntity
import com.scene.domain.repository.TvShowsRepository

class FetchPopularTvShowsUseCase constructor(private val tvShowsRepository: TvShowsRepository) {
    suspend operator fun invoke(pageIndex: Int): TvShowsResponseEntity {
        return tvShowsRepository.fetchPopularTvShows(pageIndex)
    }
}
