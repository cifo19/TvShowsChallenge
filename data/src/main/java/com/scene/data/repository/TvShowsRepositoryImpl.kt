package com.scene.data.repository

import com.scene.domain.datastore.CacheTvShowsDataStore
import com.scene.domain.datastore.RemoteTvShowsDataStore
import com.scene.domain.entity.TvShowsResponseEntity
import com.scene.domain.repository.TvShowsRepository
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
