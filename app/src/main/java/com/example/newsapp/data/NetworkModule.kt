package com.example.newsapp.data

import com.example.newsapp.domain.news.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttp() : OkHttpClient =
        OkHttpClient().newBuilder()
            .retryOnConnectionFailure(true)
            .pingInterval(30, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                var original = chain.request()
                val url = original.url
                    .newBuilder()
                    .addQueryParameter("apiKey", Constant.API_KEY)
                    .build()

                original = original.newBuilder().url(url).addHeader("Accept", "application/json").build()
                chain.proceed(original)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build()
}