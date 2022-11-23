package com.example.newsapp.domain.news.repository

import com.example.newsapp.utils.ResultState
import com.example.newsapp.data.news.remote.dto.NewsResponse
import com.example.newsapp.domain.news.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(country: String) : Flow<ResultState<List<NewsEntity>, NewsResponse>>
}