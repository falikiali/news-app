package com.example.newsapp.utils

sealed class ResultState<out T: Any, out U: Any> {
    data class Success<T: Any>(val data: T) : ResultState<T, Nothing>()
    data class Failed<U: Any>(val rawResponse: U) : ResultState<Nothing, U>()
}