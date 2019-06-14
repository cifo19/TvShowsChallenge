package com.demo.tvshows.data.model

import com.demo.tvshows.data.remote.MovieDatabaseService
import com.demo.tvshows.data.remote.response.TvShowsResponse
import io.reactivex.Single
import javax.inject.Inject

class TvShowsModel @Inject constructor(private val movieDatabaseService: MovieDatabaseService) {

    fun fetchTvShows(pageIndex: Int = 1): Single<TvShowsResponse> {
        return movieDatabaseService.getPopularTvShows(pageIndex)
    }
}
