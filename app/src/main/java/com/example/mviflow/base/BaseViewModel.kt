package com.example.mviflow.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<A : Action, S : State, R : Result>
    (initialState: S) : ViewModel() {

    private val actions: Channel<A> = Channel(Channel.UNLIMITED)

    @FlowPreview
    private val results: Flow<R> = actions.consumeAsFlow().flatMapLatest { actionToResults(it) }


    private val stateChannel: ConflatedBroadcastChannel<S> = ConflatedBroadcastChannel(initialState)


    fun sendEvent(action: A) = actions.offer(action)

    protected abstract suspend fun actionToResults(action: A): Flow<R>

    protected abstract suspend fun resultToState(result: R, state: S): S

    @ExperimentalCoroutinesApi
    val stateJob = results.scan(initialState) { state, result -> resultToState(result, state) }
        .distinctUntilChanged()
        .onEach { stateChannel.offer(it) }
        .launchIn(viewModelScope)

    fun observeState(): Flow<S> = stateChannel.asFlow()


    override fun onCleared() {
        super.onCleared()
        stateJob.cancel()
    }


}