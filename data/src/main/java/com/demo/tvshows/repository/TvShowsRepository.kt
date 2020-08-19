package com.demo.tvshows.repository

import com.demo.tvshows.cache.dao.TvShowDao
import com.demo.tvshows.remote.MovieDatabaseService
import com.demo.tvshows.remote.response.TvShowsResponse
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val movieDatabaseService: MovieDatabaseService,
    private val tvShowDao: TvShowDao
) {
    suspend fun fetchTvShows(pageIndex: Int): TvShowsResponse {
        return movieDatabaseService.getPopularTvShows(pageIndex)
    }
}
