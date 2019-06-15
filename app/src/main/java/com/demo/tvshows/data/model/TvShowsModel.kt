package com.demo.tvshows.data.model

import com.demo.tvshows.data.remote.MovieDatabaseService
import com.demo.tvshows.data.remote.response.TvShowsResponse
import com.demo.tvshows.util.network.errorhandler.ErrorHandler
import io.reactivex.Single
import javax.inject.Inject

class TvShowsModel @Inject constructor(
    private val movieDatabaseService: MovieDatabaseService,
    private val errorHandler: ErrorHandler
) {

    fun fetchTvShows(pageIndex: Int = 1): Single<TvShowsResponse> {
        return movieDatabaseService.getPopularTvShows(pageIndex)
            .onErrorResumeNext { Single.error(errorHandler.parseException(it)) }
    }
}
