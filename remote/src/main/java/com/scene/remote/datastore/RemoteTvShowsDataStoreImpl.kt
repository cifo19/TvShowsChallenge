package com.scene.remote.datastore

import com.scene.domain.entity.TvShowsResponseEntity
import com.scene.remote.mapper.TvShowsResponseRemoteToEntityMapper
import com.scene.remote.service.MovieDbService
import com.scene.domain.datastore.RemoteTvShowsDataStore
import javax.inject.Inject

class RemoteTvShowsDataStoreImpl @Inject constructor(
    private val movieDbService: MovieDbService,
    private val tvShowsResponseRemoteToEntityMapper: TvShowsResponseRemoteToEntityMapper
) : RemoteTvShowsDataStore {

    override suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity {
        return movieDbService.getPopularTvShows(pageIndex)
            .let(tvShowsResponseRemoteToEntityMapper::map)
    }
}
