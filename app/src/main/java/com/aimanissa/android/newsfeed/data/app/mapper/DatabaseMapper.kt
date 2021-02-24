package com.aimanissa.android.newsfeed.data.app.mapper

import android.util.Log
import androidx.room.TypeConverter
import com.aimanissa.android.newsfeed.data.app.db.NewsEntity
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import java.util.*

class DatabaseMapper {

    fun entityToNewsItem(entity: NewsEntity?): NewsItem? {
        if (entity == null) return null
        val newsItem = NewsItem()
        newsItem.title = entity.title
        newsItem.description = entity.description
        newsItem.urlToImage = entity.urlToImage
        newsItem.publishedAt = entityDateToNewsItemDate(entity.publishedAt)
        return newsItem
    }

    fun entityListToNewsItemsList(entities: List<NewsEntity>): List<NewsItem> {
        val newsItems = mutableListOf<NewsItem>()
        for (entity: NewsEntity in entities) {
            entityToNewsItem(entity)?.let { newsItems.add(it) }
        }
        return newsItems
    }

    fun newsItemToEntity(newsItem: NewsItem?): NewsEntity? {
        if (newsItem == null) return null
        val entity = NewsEntity()
        entity.title = newsItem.title
        entity.description = newsItem.description
        entity.urlToImage = newsItem.urlToImage
        entity.publishedAt = newsItemDateToEntityDate(newsItem.publishedAt)
        return entity
    }

    fun newsItemsListToEntityList(newsItems: List<NewsItem>): List<NewsEntity> {
        val entities = mutableListOf<NewsEntity>()
        for (newsItem: NewsItem in newsItems) {
            newsItemToEntity(newsItem)?.let { entities.add(it) }
        }
        return entities
    }

    @TypeConverter
    fun entityDateToNewsItemDate(entityDate: Long): Date {
        Log.d("DatabaseMapper", "entityDate: $entityDate")
        return Date(entityDate)
    }

    @TypeConverter
    fun newsItemDateToEntityDate(date: Date?): Long {
        Log.d("DatabaseMapper", "date: $date")
        if (date == null) {
            return 0L
        } else {
            return date.time
        }
    }
}
