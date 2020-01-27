package com.example.mviflow.network

import com.example.mviflow.repo.NewsList
import retrofit2.http.GET

interface NewsDataService {

    @GET("top-headlines?country=in&apiKey=")
    suspend fun getNewsSource(): NewsList

}