package com.demo.tvshows.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    @Suppress("PropertyName")
    protected val _onError = MutableSharedFlow<Throwable>()
    val onError: Flow<Throwable> get() = _onError

    fun launch(
        onError: (Throwable) -> Unit,
        block: suspend () -> Unit
    ) = viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }
    ) {
        block()
    }
}
