package com.example.newsapp.domain.news.usecase

import com.example.newsapp.utils.ResultState
import com.example.newsapp.data.news.remote.dto.NewsResponse
import com.example.newsapp.domain.news.entity.NewsEntity
import com.example.newsapp.domain.news.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend fun invoke(country: String) : Flow<ResultState<List<NewsEntity>, NewsResponse>> {
        return newsRepository.getNews(country)
    }
}