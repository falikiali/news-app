package com.example.newsapp.data.news

import com.example.newsapp.data.NetworkModule
import com.example.newsapp.data.news.remote.api.NewsApi
import com.example.newsapp.data.news.repository.ImplNewsRepository
import com.example.newsapp.domain.news.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class NewsModule {
    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit) : NewsApi =
        retrofit.create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideNewsRepository(newsApi: NewsApi) : NewsRepository =
        ImplNewsRepository(newsApi)

}