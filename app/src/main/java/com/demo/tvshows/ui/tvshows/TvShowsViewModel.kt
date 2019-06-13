package com.demo.tvshows.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.tvshows.data.model.TvShowsModel
import com.demo.tvshows.data.remote.response.model.TvShow
import com.demo.tvshows.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TvShowsViewModel @Inject constructor(private val tvShowsModel: TvShowsModel) : BaseViewModel() {

    private var pageIndex: Int = 1

    private val _showTvShowsLiveData = MutableLiveData<List<TvShow>>()
    val showTvShows: LiveData<List<TvShow>> = _showTvShowsLiveData

    fun getTvShows() {
        tvShowsModel.fetchTvShows(pageIndex)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(_showTvShowsLiveData::setValue) {}
            .addTo(compositeDisposable)
    }
}
