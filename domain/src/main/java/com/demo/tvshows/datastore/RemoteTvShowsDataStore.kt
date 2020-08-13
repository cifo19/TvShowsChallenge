package com.demo.tvshows.datastore

import com.demo.tvshows.entity.TvShowsResponseEntity

interface RemoteTvShowsDataStore {
    suspend fun getPopularTvShowsEntity(pageIndex: Int): TvShowsResponseEntity
}
