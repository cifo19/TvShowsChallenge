package com.scene.homedata.repository

import com.scene.homedomain.datastore.TvShowsDataStore
import com.scene.homedomain.entity.TvShowsResponseEntity
import com.scene.homedomain.repository.TvShowsRepository
import javax.inject.Inject

class TvShowsRepositoryImpl @Inject constructor(
    private val remoteTvShowsDataStore: TvShowsDataStore.Remote,
    private val localTvShowsDataStore: TvShowsDataStore.Local
) : TvShowsRepository {

    override suspend fun fetchPopularTvShows(pageIndex: Int): TvShowsResponseEntity {
        return localTvShowsDataStore.getPopularTvShowsResponseEntity(pageIndex)
            ?: remoteTvShowsDataStore.getPopularTvShowsResponseEntity(pageIndex)
                .also { localTvShowsDataStore.insertTvShowsResponseEntity(it) }
    }
}
