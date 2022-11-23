package com.example.newsapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.news.entity.NewsEntity
import com.example.newsapp.domain.news.usecase.NewsUseCase
import com.example.newsapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainViewModelContract {
    fun getNews(country: String)
}

@HiltViewModel
class MainViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel(), MainViewModelContract {
    private val _state = MutableStateFlow<MainActivityState>(MainActivityState.Init)
    val state: StateFlow<MainActivityState> get() = _state

    private val _news = MutableStateFlow<List<NewsEntity>>(mutableListOf())
    val news: StateFlow<List<NewsEntity>> get() = _news

    private fun showLoading() {
        _state.value = MainActivityState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = MainActivityState.IsLoading(false)
    }

    private fun isError(message: String) {
        _state.value = MainActivityState.Error(message)
    }

    override fun getNews(country: String) {
        viewModelScope.launch {
            newsUseCase.invoke(country)
                .onStart {
                    showLoading()
                }
                .catch {
                    hideLoading()
                    isError("Terjadi kesalahan pada jaringan!")
                }
                .collect { result ->
                    hideLoading()
                    when(result) {
                        is ResultState.Success -> _news.value = result.data
                        is ResultState.Failed -> isError("Error: " + result.rawResponse.code.toString())
                    }
                }
        }
    }
}

sealed class MainActivityState {
    object Init : MainActivityState()
    data class IsLoading(val isLoading: Boolean) : MainActivityState()
    data class Error(val message: String) : MainActivityState()
}