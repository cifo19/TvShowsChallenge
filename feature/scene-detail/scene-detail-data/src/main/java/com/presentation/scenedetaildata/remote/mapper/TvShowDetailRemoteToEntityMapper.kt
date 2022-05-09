package com.presentation.scenedetaildata.remote.mapper

import com.presentation.scenedetaildata.remote.response.TvShowDetailResponse
import com.presentation.scenedetaildomain.entity.TvShowDetailResponseEntity
import javax.inject.Inject

class TvShowDetailRemoteToEntityMapper @Inject constructor() {

    fun map(tvShowDetailResponse: TvShowDetailResponse): TvShowDetailResponseEntity {
        return TvShowDetailResponseEntity(tvShowDetailResponse.name)
    }
}
