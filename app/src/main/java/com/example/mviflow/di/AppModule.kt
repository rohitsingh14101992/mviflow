package com.example.mviflow.di

import dagger.Module

@Module(includes = [NetworkModule::class, NewsRepoModule::class])
object AppModule