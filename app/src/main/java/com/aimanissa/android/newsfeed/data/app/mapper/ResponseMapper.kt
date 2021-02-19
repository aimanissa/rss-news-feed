package com.aimanissa.android.newsfeed.data.app.mapper


import android.annotation.SuppressLint
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
        newsApiDateToNewsItemDate(newsApiModel.publishedAt)
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

    @SuppressLint("SimpleDateFormat")
    private fun newsApiDateToNewsItemDate(apiDate: String?): Date? {
        return apiDate?.let {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'").parse(it)
        }
    }
}