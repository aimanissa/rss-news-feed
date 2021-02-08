package com.aimanissa.android.newsfeed.data.app.db.repository

import com.aimanissa.android.newsfeed.data.app.db.NewsDao
import com.aimanissa.android.newsfeed.data.app.mapper.DatabaseMapper
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val mapper: DatabaseMapper,
    private val newsDao: NewsDao
) : NewsRepository {

    override fun getAll(): List<NewsItem> {
        return mapper.entityListToNewsItemsList(newsDao.getAll())
    }

    override fun saveAll(news: List<NewsItem>) {
        return newsDao.insertAll(mapper.newsItemsListToEntityList(news))
    }

    override fun getNewsByTitle(newsTitle: String): NewsItem? {
        return mapper.entityToNewsItem(newsDao.getNewsByTitle(newsTitle))
    }

    override fun deleteAll() {
        return newsDao.deleteAll()
    }
}