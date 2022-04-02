package com.scene.homedata.local.datastore

import com.scene.homedata.local.dao.TvShowsResponseDao
import com.scene.homedata.local.mapper.TvShowsResponseDataToEntityMapper
import com.scene.homedata.local.mapper.TvShowsResponseEntityToDataMapper
import com.scene.homedomain.datastore.TvShowsDataStore
import com.scene.homedomain.entity.TvShowsResponseEntity
import javax.inject.Inject

private const val TV_SHOWS_CACHE_TIME_MILLIS = 30000

class LocalTvShowsDataStoreImpl @Inject constructor(
    private val tvShowsResponseDao: TvShowsResponseDao,
    private val tvShowsResponseDataToEntityMapper: TvShowsResponseDataToEntityMapper,
    private val tvShowsResponseEntityToDataMapper: TvShowsResponseEntityToDataMapper
) : TvShowsDataStore.Local {

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
