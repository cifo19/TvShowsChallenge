package com.scene.homedomain.repository

import com.scene.homedomain.entity.TvShowsResponseEntity

interface TvShowsRepository {
    suspend fun fetchPopularTvShows(pageIndex: Int): TvShowsResponseEntity
}
