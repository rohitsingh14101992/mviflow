package com.example.mviflow.main

import com.example.mviflow.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainViewModel(state: MainState) : BaseViewModel<MainAction, MainState, MainResult>(state) {


    override suspend fun actionToResults(action: MainAction): Flow<MainResult> {
        return when (action) {
            is MainAction.A -> flow {
                emit(MainResult.AResult())
            }

            is MainAction.B -> flow {
                emit(MainResult.AResult())
            }
        }
    }

    override suspend fun resultToState(result: MainResult, state: MainState): MainState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}