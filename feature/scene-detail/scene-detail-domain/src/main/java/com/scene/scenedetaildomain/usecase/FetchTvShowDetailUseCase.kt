package com.scene.scenedetaildomain.usecase

import com.scene.scenedetaildomain.entity.TvShowDetailResponseEntity
import com.scene.scenedetaildomain.repository.TvShowDetailRepository
import javax.inject.Inject

class FetchTvShowDetailUseCase @Inject constructor(
    private val tvShowsRepository: TvShowDetailRepository
) {
    suspend fun fetchTvShowDetail(id: Int): TvShowDetailResponseEntity {
        return tvShowsRepository.fetchTvShowDetail(id)
    }
}
