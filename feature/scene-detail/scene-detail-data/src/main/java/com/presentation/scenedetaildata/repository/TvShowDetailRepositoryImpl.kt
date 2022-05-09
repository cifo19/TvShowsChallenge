package com.presentation.scenedetaildata.repository

import com.presentation.scenedetaildata.remote.TvShowDetailService
import com.presentation.scenedetaildata.remote.mapper.TvShowDetailRemoteToEntityMapper
import com.presentation.scenedetaildomain.entity.TvShowDetailResponseEntity
import com.presentation.scenedetaildomain.repository.TvShowDetailRepository
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
