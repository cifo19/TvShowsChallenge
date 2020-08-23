package com.demo.tvshows.repository

import com.demo.tvshows.dao.TvShowDao
import com.demo.tvshows.mapper.TvShowEntityMapper
import com.demo.tvshows.response.TvShowsResponse
import com.demo.tvshows.service.MovieDbService
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val movieDbService: MovieDbService,
    private val tvShowDao: TvShowDao,
    private val tvShowEntityMapper: TvShowEntityMapper
) {
    suspend fun fetchTvShows(pageIndex: Int): TvShowsResponse {
        val tvShowsResponse = movieDbService.getPopularTvShows(pageIndex)
        if (tvShowsResponse.page == 1) {
            val tvShowEntities = tvShowEntityMapper.map(tvShowsResponse)
            tvShowDao.insert(tvShowEntities)
        }
        return tvShowsResponse
    }
}
