package com.scene.scenedetaildomain.repository

import com.scene.scenedetaildomain.entity.TvShowDetailResponseEntity

interface TvShowDetailRepository {
    suspend fun fetchTvShowDetail(id: Int): TvShowDetailResponseEntity
}
