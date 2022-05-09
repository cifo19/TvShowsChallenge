package com.presentation.scenedetaildata.remote

import com.presentation.scenedetaildata.remote.response.TvShowDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowDetailService {

    @GET("tv/{id}")
    suspend fun getTvShowDetail(@Path("id") tvShowId: Int): TvShowDetailResponse
}
