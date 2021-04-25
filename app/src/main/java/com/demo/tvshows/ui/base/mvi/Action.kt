package com.demo.tvshows.ui.base.mvi

interface Action<S : State> {
    fun reduce(oldState: S): S
}
