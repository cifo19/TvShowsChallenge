package com.demo.tvshows.ui.tvshows.event

import com.demo.tvshows.ui.base.mvi.Event

sealed class TvShowsEvent : Event {
    data class FailureEvent(val throwable: Throwable) : TvShowsEvent()
}
