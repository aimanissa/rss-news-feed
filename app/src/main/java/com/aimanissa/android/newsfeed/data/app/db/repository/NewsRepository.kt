package com.aimanissa.android.newsfeed.data.app.db.repository

import com.aimanissa.android.newsfeed.data.app.model.NewsItem

interface NewsRepository {

    fun getAll(): List<NewsItem>

    fun saveAll(news: List<NewsItem>)

    fun getNewsByTitle(newsTitle: String): NewsItem?

    fun deleteAll()
}