package com.demo.tvshows.data.model

import com.demo.tvshows.data.remote.MovieDatabaseService
import com.demo.tvshows.data.remote.response.model.TvShow
import io.reactivex.Single
import javax.inject.Inject

class TvShowsModel @Inject constructor(private val movieDatabaseService: MovieDatabaseService) {

    fun fetchTvShows(pageIndex: Int = 1): Single<List<TvShow>> {
        return movieDatabaseService.getPopularTvShows(pageIndex)
            .map { it.tvShows }
    }
}
