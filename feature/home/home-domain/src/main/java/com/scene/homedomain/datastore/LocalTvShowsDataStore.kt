package com.scene.homedomain.datastore

import com.scene.homedomain.entity.TvShowsResponseEntity

interface LocalTvShowsDataStore {
    suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity?
    suspend fun insertTvShowsResponseEntity(tvShowsResponseEntity: TvShowsResponseEntity)
}
