package com.example.mviflow.di

import com.example.mviflow.network.NewsDataService
import com.example.mviflow.repo.NewsRepository
import com.example.mviflow.repo.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NewsRepoModule {

    @Provides
    @Singleton
    fun provideRepo(newsDataService: NewsDataService): NewsRepository =
        NewsRepositoryImpl(newsDataService)
}