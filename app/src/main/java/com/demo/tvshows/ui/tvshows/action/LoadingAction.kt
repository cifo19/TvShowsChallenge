package com.demo.tvshows.ui.tvshows.action

import com.demo.tvshows.ui.base.mvi.Action
import com.demo.tvshows.ui.tvshows.TvShowsState
import com.demo.tvshows.ui.tvshows.adapteritem.LoadingAdapterItem
import com.demo.tvshows.util.ext.modify

object LoadingAction : Action<TvShowsState> {
    override fun reduce(oldState: TvShowsState): TvShowsState = oldState.copy(
        adapterItems = oldState.adapterItems.modify { add(LoadingAdapterItem) }
    )
}
