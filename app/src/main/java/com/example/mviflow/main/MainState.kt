package com.example.mviflow.main

import com.example.mviflow.base.Action
import com.example.mviflow.base.Result
import com.example.mviflow.base.State

class MainState : State{

}

sealed class MainAction : Action {
    object A: MainAction()
    object B: MainAction()
}

sealed class MainResult : Result {
    data class AResult(val a : String = "") : MainResult()
}