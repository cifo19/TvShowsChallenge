package com.scene.homepresentation

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.scene.base.BaseViewModel
import com.scene.homedomain.entity.TvShowsResponseEntity
import com.scene.homedomain.usecase.FetchPopularTvShowsUseCase
import com.scene.homepresentation.adapteritem.LoadingAdapterItem
import com.scene.homepresentation.mapper.TvShowAdapterItemMapper
import com.scene.util.modifyValue
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
    val _showTvShowsLiveData = MutableLiveData<MutableList<com.scene.util.AdapterItem>>()
    val showTvShows: LiveData<MutableList<com.scene.util.AdapterItem>> = _showTvShowsLiveData

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
        return _showTvShowsLiveData.value?.contains(LoadingAdapterItem)
            ?.not() ?: true
    }
}
