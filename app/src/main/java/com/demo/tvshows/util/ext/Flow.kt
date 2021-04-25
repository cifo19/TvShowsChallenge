package com.demo.tvshows.util.ext

import androidx.lifecycle.LifecycleOwner
import com.demo.tvshows.util.coroutine.FlowObserver
import kotlinx.coroutines.flow.Flow

inline fun <reified T> Flow<T>.observeOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    noinline collector: suspend (T) -> Unit
) = FlowObserver(lifecycleOwner, this, collector)

inline fun <reified T> Flow<T>.observeOnLifecycle(
    lifecycleOwner: LifecycleOwner
) = FlowObserver(lifecycleOwner, this)
