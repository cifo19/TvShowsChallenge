package com.demo.tvshows.remote

import com.demo.tvshows.remote.response.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDatabaseService {
    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") pageIndex: Int): TvShowsResponse
}
