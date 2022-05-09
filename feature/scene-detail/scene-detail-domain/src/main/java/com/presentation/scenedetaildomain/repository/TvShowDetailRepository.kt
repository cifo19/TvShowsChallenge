package com.presentation.scenedetaildomain.repository

import com.presentation.scenedetaildomain.entity.TvShowDetailResponseEntity

interface TvShowDetailRepository {
    suspend fun fetchTvShowDetail(id: Int): TvShowDetailResponseEntity
}
