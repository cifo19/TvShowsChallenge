package com.demo.tvshows.model

import com.demo.tvshows.remote.MovieDatabaseService
import com.demo.tvshows.remote.response.TvShowsResponse
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val movieDatabaseService: MovieDatabaseService
) {
    suspend fun fetchTvShows(pageIndex: Int): TvShowsResponse {
        return movieDatabaseService.getPopularTvShows(pageIndex)
    }
}
