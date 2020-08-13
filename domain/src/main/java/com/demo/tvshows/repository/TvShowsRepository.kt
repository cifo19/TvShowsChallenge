package com.demo.tvshows.repository

import com.demo.tvshows.entity.TvShowsResponseEntity

interface TvShowsRepository {
    suspend fun fetchPopularTvShows(pageIndex: Int): TvShowsResponseEntity
}
