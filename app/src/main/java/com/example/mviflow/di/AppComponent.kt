package com.example.mviflow.di

import com.example.mviflow.NewsApp
import com.example.mviflow.repo.NewsRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: NewsApp)
    fun newsRepo(): NewsRepository
}