package com.demo.tvshows.usecase

import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.repository.TvShowsRepository

class FetchPopularTvShowsUseCase constructor(private val tvShowsRepository: TvShowsRepository) {

    suspend operator fun invoke(pageIndex: Int): TvShowsResponseEntity {
        return tvShowsRepository.fetchPopularTvShows(pageIndex)
    }
}
