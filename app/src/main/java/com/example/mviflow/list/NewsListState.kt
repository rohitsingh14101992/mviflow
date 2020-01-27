package com.example.mviflow.list

import com.example.mviflow.base.Action
import com.example.mviflow.base.Result
import com.example.mviflow.base.State
import com.example.mviflow.repo.NewsList

data class NewsListState(
    val loading: Boolean = false,
    val newsList: NewsList? = null,
    val error: Int,
    val showError: Boolean = false
) : State

sealed class NewsListAction : Action {
    object LoadNews : NewsListAction()
}

sealed class NewsListResult : Result {
    object Loading : NewsListResult()
    data class Result(val newsList: NewsList) : NewsListResult()
    data class Error(val error: Int) : NewsListResult()
}