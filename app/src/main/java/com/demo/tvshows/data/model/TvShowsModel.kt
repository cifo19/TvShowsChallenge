package com.demo.tvshows.data.model

import com.demo.tvshows.data.remote.MovieDatabaseService
import javax.inject.Inject

class TvShowsModel @Inject constructor(private val movieDatabaseService: MovieDatabaseService) {

    fun fetchTvShows(page: Int = 1) {
        movieDatabaseService.getPopularTvShows(page)
    }
}
