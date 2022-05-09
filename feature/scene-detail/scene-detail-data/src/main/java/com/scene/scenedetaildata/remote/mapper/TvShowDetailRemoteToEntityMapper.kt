package com.scene.scenedetaildata.remote.mapper

import com.scene.scenedetaildata.remote.response.TvShowDetailResponse
import com.scene.scenedetaildomain.entity.TvShowDetailResponseEntity
import javax.inject.Inject

class TvShowDetailRemoteToEntityMapper @Inject constructor() {

    fun map(tvShowDetailResponse: TvShowDetailResponse): TvShowDetailResponseEntity {
        return TvShowDetailResponseEntity(tvShowDetailResponse.name)
    }
}
