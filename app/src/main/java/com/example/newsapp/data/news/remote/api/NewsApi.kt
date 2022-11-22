package com.example.newsapp.data.news.remote.api

import com.example.newsapp.data.news.remote.dto.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getNews(@Query("country") country: String) : Response<NewsResponse>
}