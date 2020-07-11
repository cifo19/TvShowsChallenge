package com.demo.tvshows.ui.tvshows

import android.util.Log
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
import javax.inject.Inject

class TvShowsViewModel @Inject constructor(private val tvShowsModel: TvShowsModel) : BaseViewModel() {

    private var isLoading: Boolean = false
    var pageIndex: Int = 1
    var hasNextPage: Boolean = false
    val canLoadMore: Boolean
        get() = hasNextPage && isLoading.not()

    private val _showTvShowsLiveData = MutableLiveData<MutableList<AdapterItem>>()
    val showTvShows: LiveData<MutableList<AdapterItem>> = _showTvShowsLiveData

    fun getTvShows(loadMore: Boolean = false) {
        isLoading = true
        if (loadMore) pageIndex++
        Log.d("iReqStarted", pageIndex.toString())

        _showTvShowsLiveData.modifyValue { add(LoadingAdapterItem) }
        viewModelScope.launch {
            runCatching { tvShowsModel.fetchTvShows(pageIndex) }
                .onSuccess(::onTvShowsFetched)
                .onFailure(::onTvShowsFailed)
            isLoading = false
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
        Log.d("iReqFailed", throwable.toString())
        onError.value = throwable
        _showTvShowsLiveData.modifyValue { remove(LoadingAdapterItem) }
    }
}
