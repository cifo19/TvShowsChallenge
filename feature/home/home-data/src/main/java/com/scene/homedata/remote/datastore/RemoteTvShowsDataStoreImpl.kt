package com.scene.homedata.remote.datastore

import com.scene.homedata.remote.mapper.TvShowsResponseRemoteToEntityMapper
import com.scene.homedata.remote.service.MovieDbService
import com.scene.homedomain.datastore.TvShowsDataStore
import com.scene.homedomain.entity.TvShowsResponseEntity
import javax.inject.Inject

class RemoteTvShowsDataStoreImpl @Inject constructor(
    private val movieDbService: MovieDbService,
    private val tvShowsResponseRemoteToEntityMapper: TvShowsResponseRemoteToEntityMapper
) : TvShowsDataStore.Remote {

    override suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity {
        return movieDbService.getPopularTvShows(pageIndex)
            .let(tvShowsResponseRemoteToEntityMapper::map)
    }
}
