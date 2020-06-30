package com.example.mviflow.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<A : Intent, S : State, R : Result, E : Effects>
    (initialState: S) : ViewModel() {

    private val actions: Channel<A> = Channel(Channel.UNLIMITED)


    private val results: Flow<R> = actions.consumeAsFlow().flatMapLatest { actionToResults(it) }


    private val stateChannel: ConflatedBroadcastChannel<S> =
        ConflatedBroadcastChannel(initialState) //  This will overwrite the previous element

    private val viewEffects: ConflatedBroadcastChannel<E> = ConflatedBroadcastChannel()

    fun sendEvent(action: A) = actions.offer(action)

    protected abstract suspend fun actionToResults(action: A): Flow<R>

    protected abstract suspend fun resultsToViewEffect(result: R): E?

    protected abstract suspend fun resultToState(result: R, state: S): S

    val stateJob = results.onEach { results ->
        resultsToViewEffect(results)?.let {
            viewEffects.offer(it)
        }
    }.scan(initialState) { state, result -> resultToState(result, state) }
        .distinctUntilChanged()
        .onEach { stateChannel.offer(it) }
        .launchIn(viewModelScope)

    fun observeState(): Flow<S> = stateChannel.asFlow()

    fun observeViewEffects(): Flow<E> = viewEffects.asFlow()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


}