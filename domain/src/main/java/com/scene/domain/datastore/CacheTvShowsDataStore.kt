package com.scene.domain.datastore

import com.scene.domain.entity.TvShowsResponseEntity

interface CacheTvShowsDataStore {
    suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity?
    suspend fun insertTvShowsResponseEntity(tvShowsResponseEntity: TvShowsResponseEntity)
}
