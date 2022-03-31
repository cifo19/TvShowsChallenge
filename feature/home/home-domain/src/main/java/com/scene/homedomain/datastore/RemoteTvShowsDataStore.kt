package com.scene.homedomain.datastore

import com.scene.homedomain.entity.TvShowsResponseEntity

interface RemoteTvShowsDataStore {
    suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity
}
