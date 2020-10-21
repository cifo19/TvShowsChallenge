package com.demo.tvshows.datastore

import com.demo.tvshows.entity.TvShowsResponseEntity

interface RemoteTvShowsDataStore {
    suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity
}
