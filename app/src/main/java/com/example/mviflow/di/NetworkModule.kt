package com.example.mviflow.di

import com.example.mviflow.network.NewsDataService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://newsapi.org/v2/"

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideNewsService(): NewsDataService {

        val interceptor = (HttpLoggingInterceptor())
            .apply { level = HttpLoggingInterceptor.Level.BODY }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsDataService::class.java)
    }
}