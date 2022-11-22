package com.example.newsapp.data.news.repository

import com.example.newsapp.domain.news.utils.ResultState
import com.example.newsapp.data.news.remote.api.NewsApi
import com.example.newsapp.data.news.remote.dto.NewsResponse
import com.example.newsapp.domain.news.entity.NewsEntity
import com.example.newsapp.domain.news.entity.SourceEntity
import com.example.newsapp.domain.news.repository.NewsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImplNewsRepository @Inject constructor(private val newsApi: NewsApi) : NewsRepository {
    override suspend fun getNews(country: String): Flow<ResultState<List<NewsEntity>, NewsResponse>> {
        return flow {
            val response = newsApi.getNews(country)
            if (response.isSuccessful) {
                val body = response.body()
                val news = mutableListOf<NewsEntity>()

                body?.articles?.forEach {
                    if (it != null) {
                        news.add(
                            NewsEntity(
                                it.publishedAt,
                                it.author,
                                it.urlToImage,
                                it.description,
                                SourceEntity(
                                    it.source?.name,
                                    it.source?.id
                                ),
                                it.title,
                                it.url,
                                it.content
                            )
                        )
                    }
                }

                emit(ResultState.Success(news))
            } else {
                val type = object : TypeToken<NewsResponse>(){}.type
                val err = Gson().fromJson<NewsResponse>(response.errorBody()!!.charStream(), type)!!
                err.code = response.code()
                emit(ResultState.Failed(err))
            }
        }
    }
}