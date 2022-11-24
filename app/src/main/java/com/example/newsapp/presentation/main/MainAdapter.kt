package com.example.newsapp.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ListNewsBinding
import com.example.newsapp.domain.news.entity.NewsEntity
import com.squareup.picasso.Picasso

class MainAdapter : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {
    private var listNews = ArrayList<NewsEntity>()
    var onItemClick: ((NewsEntity) -> Unit)? = null

    fun setData(newListNews: List<NewsEntity>?) {
        if (newListNews == null) return
        listNews.clear()
        listNews.addAll(newListNews)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListNewsBinding.bind(itemView)

        fun bind(data: NewsEntity) {
            with(binding) {
                tvAuthor.text = data.name
                tvTitle.text = data.title
                Picasso.get()
                    .load(data.urlToImage)
                    .into(ivImage)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listNews[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_news, parent, false))
    }

    override fun onBindViewHolder(holder: MainAdapter.ListViewHolder, position: Int) {
        val data = listNews[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }
}