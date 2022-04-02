package com.scene.homedomain.datastore

import com.scene.homedomain.entity.TvShowsResponseEntity

interface TvShowsDataStore {

    interface Local {
        suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity?
        suspend fun insertTvShowsResponseEntity(tvShowsResponseEntity: TvShowsResponseEntity)
    }

    interface Remote {
        suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity
    }
}
