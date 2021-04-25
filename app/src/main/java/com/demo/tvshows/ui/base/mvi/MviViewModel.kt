package com.demo.tvshows.ui.base.mvi

import androidx.lifecycle.viewModelScope
import com.demo.tvshows.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

open class MviViewModel<S : State, E : Event>(initialState: S) : BaseViewModel() {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    private val _event = Channel<E>(Channel.BUFFERED)
    private val _action = Channel<Action<S>>()

    val states = _state.asStateFlow()
    val events = _event.receiveAsFlow()

    init {
        observeActions()
    }

    protected fun sendAction(action: Action<S>) {
        _action.offer(action)
    }

    protected fun sendEvent(event: E) {
        _event.offer(event)
    }

    private fun observeActions() {
        _action.consumeAsFlow()
            .map { action -> action.reduce(_state.value) }
            .onEach { state -> _state.value = state }
            .launchIn(viewModelScope)
    }
}
