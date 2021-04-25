package com.demo.tvshows.util.coroutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055

class FlowObserver<T>(
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit = {}
) {

    private var job: Job? = null

    private val lifecycleEventObserver = LifecycleEventObserver { source, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                source.lifecycleScope.launch { flow.collect { collector(it) } }
            }
            Lifecycle.Event.ON_STOP -> {
                job?.cancel()
                job = null
            }
            else -> {
            }
        }
    }

    init {
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)
    }
}
