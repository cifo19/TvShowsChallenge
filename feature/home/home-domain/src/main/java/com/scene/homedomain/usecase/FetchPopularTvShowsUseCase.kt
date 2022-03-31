package com.scene.homedomain.usecase

import com.scene.homedomain.entity.TvShowsResponseEntity
import com.scene.homedomain.repository.TvShowsRepository
import javax.inject.Inject

class FetchPopularTvShowsUseCase @Inject constructor(private val tvShowsRepository: TvShowsRepository) {
    suspend operator fun invoke(pageIndex: Int): TvShowsResponseEntity {
        return tvShowsRepository.fetchPopularTvShows(pageIndex)
    }
}
