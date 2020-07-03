package com.demo.tvshows.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val onError = MutableLiveData<Throwable>()
}
