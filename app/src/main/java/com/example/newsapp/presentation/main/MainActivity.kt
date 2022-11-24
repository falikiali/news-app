package com.example.newsapp.presentation.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var country: String
    private val mainAdapter = MainAdapter()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        country = "id"

        initRecyclerView()
        getNews(country)
        observeNews()
        actionEachItem()
        observeState()
    }

    private fun initRecyclerView() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mainAdapter
            setHasFixedSize(true)
        }
    }

    private fun getNews(country: String) {
        mainViewModel.getNews(country)
    }

    private fun observeNews() {
        mainViewModel.news
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                mainAdapter.setData(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun actionEachItem() {
        mainAdapter.onItemClick = {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it.url)
            startActivity(intent)
        }
    }

    private fun observeState() {
        mainViewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                when(state) {
                    is MainActivityState.Init -> Unit
                    is MainActivityState.IsLoading -> handleLoading(state.isLoading)
                    is MainActivityState.Error -> Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmer.startShimmer()
            binding.shimmer.visibility = View.VISIBLE
            binding.rvNews.visibility = View.GONE
        } else {
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
            binding.rvNews.visibility = View.VISIBLE
        }
    }
}