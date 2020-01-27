package com.example.mviflow.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mviflow.repo.NewsRepository

class NewsListViewModelFactory(val newsRepository: NewsRepository, val state: NewsListState) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel(state, newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}