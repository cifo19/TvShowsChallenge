package com.scene.domain.datastore

import com.scene.domain.entity.TvShowsResponseEntity

interface RemoteTvShowsDataStore {
    suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity
}
