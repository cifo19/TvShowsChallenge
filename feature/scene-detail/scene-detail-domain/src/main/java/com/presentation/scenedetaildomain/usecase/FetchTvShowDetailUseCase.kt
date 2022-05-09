package com.presentation.scenedetaildomain.usecase

import com.presentation.scenedetaildomain.entity.TvShowDetailResponseEntity
import com.presentation.scenedetaildomain.repository.TvShowDetailRepository
import javax.inject.Inject

class FetchTvShowDetailUseCase @Inject constructor(
    private val tvShowsRepository: TvShowDetailRepository
) {
    suspend fun fetchTvShowDetail(id: Int): TvShowDetailResponseEntity {
        return tvShowsRepository.fetchTvShowDetail(id)
    }
}
