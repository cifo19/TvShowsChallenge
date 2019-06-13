package com.demo.tvshows.data.remote

import com.demo.tvshows.data.remote.response.TvShowsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDatabaseService {

    @GET("tv/popular")
    fun getPopularTvShows(@Query("page") page: Int): Single<TvShowsResponse>
}
