package com.example.mviflow.list

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.mviflow.R
import com.example.mviflow.base.BaseViewModel
import com.example.mviflow.base.Lce
import com.example.mviflow.repo.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NewsListViewModel(state: NewsListState, val newsRepository: NewsRepository) :
    BaseViewModel<NewsListAction, NewsListState, NewsListResult>(state) {


    val state = ObservableField<NewsListState>(state)

    init {
        subscribeToViewState()
    }

    private fun subscribeToViewState() {
        viewModelScope.launch {
            observeState().collect {
                state.set(it)
            }
        }
    }


    override suspend fun actionToResults(action: NewsListAction): Flow<NewsListResult> {
        return when (action) {
            is NewsListAction.LoadNews ->
                newsRepository.getNewsSource().map {
                    when (it) {
                        is Lce.Loading -> NewsListResult.Loading

                        is Lce.Content -> NewsListResult.Result(it.packet)

                        else -> NewsListResult.Error(R.string.app_name)
                    }
                }.flowOn(Dispatchers.IO)
        }
    }

    override suspend fun resultToState(
        result: NewsListResult,
        state: NewsListState
    ): NewsListState {
        return when (result) {
            is NewsListResult.Loading -> state.copy(loading = true, showError = false)

            is NewsListResult.Result -> state.copy(
                loading = false,
                newsList = result.newsList,
                showError = false
            )

            is NewsListResult.Error -> state.copy(
                loading = false,
                error = result.error,
                showError = true
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bind:response")
        fun bindListAdapter(reyclerView: RecyclerView, state: NewsListState) {
            if (state.newsList != null)
                (reyclerView.adapter as NewsListAdapter).updateList(state.newsList.articles)
        }

        @JvmStatic
        @BindingAdapter("bind_visibility")
        fun bindVisibility(view: View, visibility: Boolean?) {
            view.visibility = if (visibility == true) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }


}