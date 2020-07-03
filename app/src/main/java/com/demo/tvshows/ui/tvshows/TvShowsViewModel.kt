package com.demo.tvshows.ui.tvshows

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.tvshows.data.model.TvShowsModel
import com.demo.tvshows.ui.base.BaseViewModel
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.TvShowAdapterItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowsViewModel @Inject constructor(private val tvShowsModel: TvShowsModel) : BaseViewModel() {

    @VisibleForTesting
    var pageIndex: Int = 1

    @VisibleForTesting
    var fetchingTvShows = false

    var hasNextPage: Boolean = false

    private val _showTvShowsLiveData = MutableLiveData<List<TvShowAdapterItem>>()
    val showTvShows: LiveData<List<TvShowAdapterItem>> = _showTvShowsLiveData
    private val _toggleListLoading = MutableLiveData<Boolean>()
    val toggleListLoading: LiveData<Boolean> = _toggleListLoading

    @Suppress("TooGenericExceptionCaught")
    fun getTvShows(loadMore: Boolean = false) {
        if (fetchingTvShows) return
        fetchingTvShows = true
        _toggleListLoading.value = true

        if (loadMore) {
            pageIndex++
        }

        viewModelScope.launch {
            try {
                val tvShows = tvShowsModel.fetchTvShows(pageIndex)
                hasNextPage = tvShows.page != tvShows.totalPages
                _showTvShowsLiveData.value = tvShows.tvShows.map(::TvShowAdapterItem)
            } catch (exception: Exception) {
                _toggleListLoading.value = false
                onError.value = exception
            }

            fetchingTvShows = false
        }
    }
}
