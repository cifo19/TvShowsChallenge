package com.scene.homedata.repository


import com.scene.homedomain.datastore.LocalTvShowsDataStore
import com.scene.homedomain.datastore.RemoteTvShowsDataStore
import com.scene.homedomain.entity.TvShowsResponseEntity
import com.scene.homedomain.repository.TvShowsRepository
import javax.inject.Inject

class TvShowsRepositoryImpl @Inject constructor(
    private val remoteTvShowsDataStoreImpl: RemoteTvShowsDataStore,
    private val cacheTvShowsDataStoreImpl: LocalTvShowsDataStore
) : TvShowsRepository {

    override suspend fun fetchPopularTvShows(pageIndex: Int): TvShowsResponseEntity {
        return cacheTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(pageIndex)
            ?: remoteTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(pageIndex)
                .also { cacheTvShowsDataStoreImpl.insertTvShowsResponseEntity(it) }
    }
}
