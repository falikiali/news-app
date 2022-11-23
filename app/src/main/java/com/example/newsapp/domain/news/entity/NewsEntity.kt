package com.example.newsapp.domain.news.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsEntity(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val name: String? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null
) : Parcelable