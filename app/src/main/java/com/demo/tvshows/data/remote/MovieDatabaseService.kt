package com.demo.tvshows.data.remote

import com.demo.tvshows.data.remote.response.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDatabaseService {

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") pageIndex: Int): TvShowsResponse
}
