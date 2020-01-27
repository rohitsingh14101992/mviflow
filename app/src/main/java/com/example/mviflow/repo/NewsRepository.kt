package com.example.mviflow.repo

import com.example.mviflow.base.Lce
import com.example.mviflow.network.NewsDataService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(val newsService: NewsDataService) :
    NewsRepository {

    override suspend fun getNewsSource(): Flow<Lce<NewsList>> {
        return flow {
            emit(Lce.Loading<NewsList>())
            emit(Lce.Content(newsService.getNewsSource()))
        }.catch {
            emit(Lce.Error(it))
        }
    }
}


interface NewsRepository {
    suspend fun getNewsSource(): Flow<Lce<NewsList>>
}