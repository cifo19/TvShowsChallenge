package com.demo.tvshows.ui.tvshows

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.tvshows.data.model.TvShowsModel
import com.demo.tvshows.ui.base.BaseViewModel
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.TvShowAdapterItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TvShowsViewModel @Inject constructor(private val tvShowsModel: TvShowsModel) : BaseViewModel() {

    @VisibleForTesting
    var pageIndex: Int = 1
    @VisibleForTesting
    var fetchingTvShows = false

    var hasNextPage: Boolean = false

    private val _showTvShowsLiveData = MutableLiveData<MutableList<TvShowAdapterItem>>()
    val showTvShows: LiveData<MutableList<TvShowAdapterItem>> = _showTvShowsLiveData
    private val _toggleListLoading = MutableLiveData<Boolean>()
    val toggleListLoading: LiveData<Boolean> = _toggleListLoading

    fun getTvShows(loadMore: Boolean = false) {
        if (fetchingTvShows) return
        fetchingTvShows = true
        _toggleListLoading.value = true

        if (loadMore) {
            pageIndex++
        }

        tvShowsModel.fetchTvShows(pageIndex)
            .doOnSuccess { hasNextPage = it.page != it.totalPages }
            .flattenAsObservable { it.tvShows }
            .map { TvShowAdapterItem(it) }
            .toList()
            .doAfterTerminate { fetchingTvShows = false }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { _showTvShowsLiveData.value = it },
                {
                    _toggleListLoading.value = false
                    onError.value = it
                }
            )
            .addTo(compositeDisposable)
    }
}
