package com.example.newsapp.data.news.remote.api

import com.example.newsapp.data.news.remote.dto.NewsResponse
import com.example.newsapp.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = Constant.API_KEY
    ) : Response<NewsResponse>
}