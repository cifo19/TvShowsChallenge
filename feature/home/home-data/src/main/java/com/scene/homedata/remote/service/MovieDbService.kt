package com.scene.homedata.remote.service

import com.scene.homedata.remote.response.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbService {
    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") pageIndex: Int): TvShowsResponse
}
