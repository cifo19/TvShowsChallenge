package com.demo.tvshows.ui.paging

import androidx.paging.PagingSource
import com.demo.tvshows.data.remote.MovieDatabaseService
import com.demo.tvshows.data.remote.response.model.TvShow
import com.demo.tvshows.util.network.errorhandler.ServiceException

private const val INITIAL_PAGE_KEY = 1

class TvShowsPagingSource(
    private val movieDatabaseService: MovieDatabaseService
) : PagingSource<Int, TvShow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val position = params.key ?: INITIAL_PAGE_KEY
        return try {
            val response = movieDatabaseService.getPopularTvShows(position)
            val tvShows = response.tvShows
            LoadResult.Page(
                data = tvShows,
                prevKey = if (position == INITIAL_PAGE_KEY) null else position - 1,
                nextKey = if (response.tvShows.isEmpty()) null else position + 1
            )
        } catch (exception: ServiceException) {
            return LoadResult.Error(exception)
        }
    }
}
