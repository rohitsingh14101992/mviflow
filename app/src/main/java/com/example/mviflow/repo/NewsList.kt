package com.example.mviflow.repo

import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("status") val status: String,
    @SerializedName("source") val source: String,
    @SerializedName("sortBy") val sortBy: String,
    @SerializedName("articles") val articles: List<NewsArticles>
)

data class NewsArticles(
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String
)