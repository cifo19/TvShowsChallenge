package com.demo.tvshows.repository

import com.demo.tvshows.datastore.CacheTvShowsDataStore
import com.demo.tvshows.datastore.RemoteTvShowsDataStore
import com.demo.tvshows.entity.TvShowsResponseEntity
import javax.inject.Inject

class TvShowsRepositoryImpl @Inject constructor(
    private val remoteTvShowsDataStoreImpl: RemoteTvShowsDataStore,
    private val cacheTvShowsDataStoreImpl: CacheTvShowsDataStore
) : TvShowsRepository {
    override suspend fun fetchPopularTvShows(pageIndex: Int): TvShowsResponseEntity {
        return cacheTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(pageIndex)
            ?: remoteTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(pageIndex)
                .also { cacheTvShowsDataStoreImpl.insertTvShowsResponseEntity(it) }
    }
}
