package com.demo.tvshows.ui.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.tvshows.data.model.TvShowsModel
import com.demo.tvshows.data.remote.response.TvShowsResponse
import com.demo.tvshows.ui.base.BaseViewModel
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.LoadingAdapterItem
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.TvShowAdapterItem
import com.demo.tvshows.util.modifyValue
import kotlinx.coroutines.launch

class TvShowsViewModel @ViewModelInject constructor(
    private val tvShowsModel: TvShowsModel
) : BaseViewModel() {

    var pageIndex: Int = 1
    var hasNextPage: Boolean = false
    val canLoadMore: Boolean get() = hasNextPage && isLoading()

    private val _showTvShowsLiveData = MutableLiveData<MutableList<AdapterItem>>()
    val showTvShows: LiveData<MutableList<AdapterItem>> = _showTvShowsLiveData

    fun getTvShows(loadMore: Boolean = false) {
        if (loadMore) pageIndex++

        _showTvShowsLiveData.modifyValue { add(LoadingAdapterItem) }
        viewModelScope.launch {
            runCatching { tvShowsModel.fetchTvShows(pageIndex) }
                .onSuccess(::onTvShowsFetched)
                .onFailure(::onTvShowsFailed)
        }
    }

    private fun onTvShowsFetched(tvShowsResponse: TvShowsResponse) {
        hasNextPage = tvShowsResponse.page != tvShowsResponse.totalPages
        _showTvShowsLiveData.modifyValue {
            remove(LoadingAdapterItem)
            addAll(tvShowsResponse.tvShows.map(::TvShowAdapterItem))
        }
    }

    private fun onTvShowsFailed(throwable: Throwable) {
        onError.value = throwable
        _showTvShowsLiveData.modifyValue { remove(LoadingAdapterItem) }
    }

    private fun isLoading(): Boolean {
        return _showTvShowsLiveData.value?.contains(LoadingAdapterItem)?.not() ?: true
    }
}
