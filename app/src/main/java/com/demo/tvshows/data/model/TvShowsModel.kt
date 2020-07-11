package com.demo.tvshows.data.model

import com.demo.tvshows.data.remote.MovieDatabaseService
import com.demo.tvshows.data.remote.response.TvShowsResponse
import javax.inject.Inject

class TvShowsModel @Inject constructor(
    private val movieDatabaseService: MovieDatabaseService
) {
    suspend fun fetchTvShows(pageIndex: Int = 1) : TvShowsResponse {
        return movieDatabaseService.getPopularTvShows(pageIndex)
    }
}
