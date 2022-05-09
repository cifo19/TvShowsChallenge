package com.scene.scenedetaildata.repository

import com.scene.scenedetaildata.remote.TvShowDetailService
import com.scene.scenedetaildata.remote.mapper.TvShowDetailRemoteToEntityMapper
import com.scene.scenedetaildomain.entity.TvShowDetailResponseEntity
import com.scene.scenedetaildomain.repository.TvShowDetailRepository
import javax.inject.Inject

class TvShowDetailRepositoryImpl @Inject constructor(
    private val tvShowDetailService: TvShowDetailService,
    private val tvShowDetailRemoteToEntityMapper: TvShowDetailRemoteToEntityMapper
) : TvShowDetailRepository {

    override suspend fun fetchTvShowDetail(id: Int): TvShowDetailResponseEntity {
        val tvShowDetail = tvShowDetailService.getTvShowDetail(id)
        return tvShowDetailRemoteToEntityMapper.map(tvShowDetail)
    }
}
