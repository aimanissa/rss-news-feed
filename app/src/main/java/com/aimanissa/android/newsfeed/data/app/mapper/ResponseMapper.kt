package com.aimanissa.android.newsfeed.data.app.mapper

import android.util.Log
import com.aimanissa.android.newsfeed.data.app.api.NewsApiModel
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import java.text.SimpleDateFormat
import java.util.*

class ResponseMapper {

    fun newsApiToNewsItem(newsApiModel: NewsApiModel?): NewsItem? {
        if (newsApiModel == null) return null
        val newsItem = NewsItem()
        newsItem.title = newsApiModel.title.toString()
        newsItem.description = newsApiModel.description.toString()
        newsItem.urlToImage = newsApiModel.urlToImage.toString()
        newsItem.publishedAt = newsApiDateToNewsItemDate(newsApiModel.publishedAt.toString())!!
        return newsItem
    }

    fun newsApiListToNewsItemsList(newsApiList: List<NewsApiModel>): List<NewsItem> {
        val newsItems = mutableListOf<NewsItem>()
        for (newsApi: NewsApiModel in newsApiList) {
            newsApiList.filterNot {
                it.title.isNullOrEmpty() || it.description.isNullOrEmpty() ||
                        it.urlToImage.isNullOrEmpty() || it.publishedAt.isNullOrEmpty()
            }
            newsApiToNewsItem(newsApi)?.let { newsItems.add(it) }
        }
        return newsItems
    }

    private fun newsApiDateToNewsItemDate(apiDate: String): Date? {
        Log.d("ResponseMapper", "apiDate: $apiDate")
        return SimpleDateFormat(PATTERN, Locale.US).parse(apiDate)
    }

    companion object {
        private const val PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    }
}