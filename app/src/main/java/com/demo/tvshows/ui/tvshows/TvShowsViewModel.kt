package com.demo.tvshows.ui.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.ui.base.mvi.MviViewModel
import com.demo.tvshows.ui.base.mvi.State
import com.demo.tvshows.ui.tvshows.action.FailureAction
import com.demo.tvshows.ui.tvshows.action.LoadingAction
import com.demo.tvshows.ui.tvshows.action.SuccessAction
import com.demo.tvshows.ui.tvshows.adapteritem.LoadingAdapterItem
import com.demo.tvshows.ui.tvshows.event.TvShowsEvent
import com.demo.tvshows.ui.tvshows.mapper.TvShowAdapterItemMapper
import com.demo.tvshows.usecase.FetchPopularTvShowsUseCase
import com.demo.tvshows.util.AdapterItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class TvShowsState(val adapterItems: List<AdapterItem>) : State

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalStdlibApi::class)
class TvShowsViewModel @ViewModelInject constructor(
    private val fetchPopularTvShowsUseCase: FetchPopularTvShowsUseCase,
    private val tvShowAdapterItemMapper: TvShowAdapterItemMapper
) : MviViewModel<TvShowsState, TvShowsEvent>(TvShowsState(listOf(LoadingAdapterItem))) {

    private var pageIndex: Int = 1
    private var hasNextPage: Boolean = false

    val canLoadMore: Boolean get() = hasNextPage && isLoading().not()

    fun getTvShows(loadMore: Boolean = false) {
        if (loadMore) onLoadMore()

        launch(onError = ::onTvShowsFailed) {
            val tvShowsResponseEntity = fetchPopularTvShowsUseCase(pageIndex)
            onTvShowsFetched(tvShowsResponseEntity)
        }
    }

    private fun onLoadMore() {
        pageIndex++
        sendAction(LoadingAction)
    }

    private fun onTvShowsFetched(tvShowsResponseEntity: TvShowsResponseEntity) {
        hasNextPage = tvShowsResponseEntity.page != tvShowsResponseEntity.totalPages
        val adapterItems = tvShowAdapterItemMapper.map(tvShowsResponseEntity.tvShowEntities)
        sendAction(SuccessAction(adapterItems))
    }

    private fun onTvShowsFailed(throwable: Throwable) {
        sendEvent(TvShowsEvent.FailureEvent(throwable))
        sendAction(FailureAction)
    }

    private fun isLoading() = states.value.adapterItems.contains(LoadingAdapterItem)
}
