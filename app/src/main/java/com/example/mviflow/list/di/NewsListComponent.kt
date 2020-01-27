package com.example.mviflow.list.di

import com.example.mviflow.R
import com.example.mviflow.di.AppComponent
import com.example.mviflow.list.NewsListActivity
import com.example.mviflow.list.NewsListState
import com.example.mviflow.list.NewsListViewModelFactory
import com.example.mviflow.repo.NewsRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@ActivityScope
@Component(modules = [NewsListActivityModule::class], dependencies = [AppComponent::class])
interface NewsListComponent {
    fun inject(newsListActivity: NewsListActivity)
}

@Module
object NewsListActivityModule {

    @Provides
    @ActivityScope
    fun provideViewModelFactory(
        repository: NewsRepository,
        state: NewsListState
    ): NewsListViewModelFactory =
        NewsListViewModelFactory(repository, state)

    @Provides
    @ActivityScope
    fun provideIntitalState(): NewsListState = NewsListState(error = R.string.error_msg)
}

@Scope
@Retention
annotation class ActivityScope
