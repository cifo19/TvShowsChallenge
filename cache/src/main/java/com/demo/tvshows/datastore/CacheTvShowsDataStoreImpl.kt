package com.demo.tvshows.datastore

import com.demo.tvshows.dao.TvShowsResponseDao
import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.mapper.TvShowsResponseDataToEntityMapper
import com.demo.tvshows.mapper.TvShowsResponseEntityToDataMapper
import javax.inject.Inject

private const val TV_SHOWS_CACHE_TIME_MILLIS = 30000

class CacheTvShowsDataStoreImpl @Inject constructor(
    private val tvShowsResponseDao: TvShowsResponseDao,
    private val tvShowsResponseDataToEntityMapper: TvShowsResponseDataToEntityMapper,
    private val tvShowsResponseEntityToDataMapper: TvShowsResponseEntityToDataMapper
) : CacheTvShowsDataStore {

    override suspend fun getPopularTvShowsResponseEntity(pageIndex: Int): TvShowsResponseEntity? {
        val cacheExpired = isTvShowResponseEntityCacheExpired(pageIndex)
        return if (cacheExpired) {
            tvShowsResponseDao.deleteTvShowResponseData(pageIndex)
            null
        } else {
            tvShowsResponseDao.getTvShowResponseEntities(pageIndex)
                .let(tvShowsResponseDataToEntityMapper::map)
        }
    }

    override suspend fun insertTvShowsResponseEntity(tvShowsResponseEntity: TvShowsResponseEntity) {
        tvShowsResponseEntityToDataMapper.map(tvShowsResponseEntity).also {
            tvShowsResponseDao.insertTvShowsResponse(it)
        }
    }

    private suspend fun isTvShowResponseEntityCacheExpired(pageIndex: Int): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastCreationTime = tvShowsResponseDao.getTvShowResponseEntityCreationTime(pageIndex) ?: 0
        return currentTime - TV_SHOWS_CACHE_TIME_MILLIS > lastCreationTime
    }
}
