package com.aimanissa.android.newsfeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.databinding.NewsItemBinding
import com.squareup.picasso.Picasso

class NewsAdapter(private var newsItems: ArrayList<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = NewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        with(holder) {
            with(newsItems[position]) {
                newsItemBinding.newsTitle.text = title
                newsItemBinding.newsDescription.text = description
                Picasso.get()
                    .load(urlToImage)
                    .into(newsItemBinding.newsImageView)

                holder.newsItemBinding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = newsItems.size

    fun updateNews(updatedNews: List<NewsItem>) {
        newsItems.clear()
        newsItems.addAll(updatedNews)
        notifyDataSetChanged()
    }

    inner class NewsHolder(val newsItemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(newsItemBinding.root)

    interface OnItemClickListener {
        fun onItemClick(newsItem: NewsItem)
    }
}













