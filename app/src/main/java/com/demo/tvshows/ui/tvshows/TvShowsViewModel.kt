package com.demo.tvshows.ui.tvshows

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.demo.tvshows.data.remote.response.model.TvShow
import com.demo.tvshows.ui.base.BaseViewModel
import com.demo.tvshows.ui.paging.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowsViewModel @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) : BaseViewModel() {

    private var currentSearchResult: Flow<PagingData<TvShow>>? = null

    fun fetchTvShows(): Flow<PagingData<TvShow>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }

        return tvShowsRepository.getTvShows()
            .cachedIn(viewModelScope)
            .also(this::currentSearchResult::set)
    }
}
