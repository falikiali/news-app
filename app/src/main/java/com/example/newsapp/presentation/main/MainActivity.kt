package com.example.newsapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var country: String
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        country = "id"

        getNews(country)
        observeNews()
    }

    private fun getNews(country: String) {
        mainViewModel.getNews(country)
    }

    private fun observeNews() {
        mainViewModel.news
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                Log.d("Main Activity", it.toString())
            }
            .launchIn(lifecycleScope)
    }
}