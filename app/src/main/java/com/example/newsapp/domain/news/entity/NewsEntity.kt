package com.example.newsapp.domain.news.entity

data class NewsEntity(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val source: SourceEntity? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null
)

data class SourceEntity(
    val name: String? = null,
    val id: Any? = null
)
