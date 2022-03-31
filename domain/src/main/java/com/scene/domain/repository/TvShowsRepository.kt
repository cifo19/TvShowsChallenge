package com.scene.domain.repository

import com.scene.domain.entity.TvShowsResponseEntity

interface TvShowsRepository {
    suspend fun fetchPopularTvShows(pageIndex: Int): TvShowsResponseEntity
}
