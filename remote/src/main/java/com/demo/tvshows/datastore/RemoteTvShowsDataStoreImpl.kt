package com.demo.tvshows.datastore

import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.mapper.TvShowsResponseRemoteToEntityMapper
import com.demo.tvshows.service.MovieDbService
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
