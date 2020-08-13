package com.demo.tvshows.datastore

import com.demo.tvshows.entity.TvShowsResponseEntity

interface CacheTvShowsDataStore {
    suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity?
    suspend fun insertTvShowsResponseEntity(tvShowsResponseEntity: TvShowsResponseEntity)
}
