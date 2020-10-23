package com.demo.tvshows.ui.tvshows

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.ui.base.BaseViewModel
import com.demo.tvshows.ui.tvshows.adapteritem.LoadingAdapterItem
import com.demo.tvshows.ui.tvshows.mapper.TvShowAdapterItemMapper
import com.demo.tvshows.usecase.FetchPopularTvShowsUseCase
import com.demo.tvshows.util.AdapterItem
import com.demo.tvshows.util.modifyValue
import kotlinx.coroutines.launch

class TvShowsViewModel @ViewModelInject constructor(
    private val fetchPopularTvShowsUseCase: FetchPopularTvShowsUseCase,
    private val tvShowAdapterItemMapper: TvShowAdapterItemMapper
) : BaseViewModel() {

    val canLoadMore: Boolean get() = hasNextPage && isLoading()

    @VisibleForTesting
    var pageIndex: Int = 1

    @VisibleForTesting
    var hasNextPage: Boolean = false

    @VisibleForTesting
    @Suppress("PropertyName", "VariableNaming")
    val _showTvShowsLiveData = MutableLiveData<MutableList<AdapterItem>>()
    val showTvShows: LiveData<MutableList<AdapterItem>> = _showTvShowsLiveData

    fun getTvShows(loadMore: Boolean = false) {
        if (loadMore) pageIndex++

        _showTvShowsLiveData.modifyValue { add(LoadingAdapterItem) }
        viewModelScope.launch {
            runCatching { fetchPopularTvShowsUseCase(pageIndex) }
                .onSuccess(::onTvShowsFetched)
                .onFailure(::onTvShowsFailed)
        }
    }

    private fun onTvShowsFetched(tvShowsResponseEntity: TvShowsResponseEntity) {
        hasNextPage = tvShowsResponseEntity.page != tvShowsResponseEntity.totalPages
        _showTvShowsLiveData.modifyValue {
            remove(LoadingAdapterItem)
            addAll(tvShowAdapterItemMapper.map(tvShowsResponseEntity.tvShowEntities))
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
