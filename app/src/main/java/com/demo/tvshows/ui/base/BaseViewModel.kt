package com.demo.tvshows.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val onError = MutableLiveData<Throwable>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
