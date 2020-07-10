package com.demo.tvshows.ui.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.demo.tvshows.data.remote.MovieDatabaseService
import com.demo.tvshows.data.remote.response.model.TvShow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowsRepository @Inject constructor(private val movieDatabaseService: MovieDatabaseService) {

    fun getTvShows(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TvShowsPagingSource(movieDatabaseService) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}
