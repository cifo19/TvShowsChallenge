package com.demo.tvshows.ui.tvshows.tvshowdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.tvshows.ui.base.BaseViewModel

class TvShowDetailViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val viewState = MutableLiveData<TvShowDetailViewState>()
    fun getViewState(): LiveData<TvShowDetailViewState> = viewState

    fun init(tvShowId: Int) {
        viewState.value = TvShowDetailViewState(tvShowId)
    }
}
