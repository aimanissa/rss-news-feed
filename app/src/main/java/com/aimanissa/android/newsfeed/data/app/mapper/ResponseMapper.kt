package com.aimanissa.android.newsfeed.data.app.mapper

import com.aimanissa.android.newsfeed.data.app.api.NewsApiModel
import com.aimanissa.android.newsfeed.data.app.model.NewsItem

class ResponseMapper {

    fun newsApiToNewsItem(newsApiModel: NewsApiModel?): NewsItem? {
        if (newsApiModel == null) return null
        val newsItem = NewsItem()
        newsItem.title = newsApiModel.title.toString()
        newsItem.description = newsApiModel.description.toString()
        newsItem.urlToImage = newsApiModel.urlToImage.toString()
        return newsItem
    }

    fun newsApiListToNewsItemsList(newsApiList: List<NewsApiModel>): List<NewsItem> {
        val newsItems = mutableListOf<NewsItem>()
        newsApiList.filterNot {
            it.title.isNullOrEmpty() || it.description.isNullOrEmpty() || it.urlToImage.isNullOrEmpty()
        }
        for (newsApi: NewsApiModel in newsApiList) {
            newsApiToNewsItem(newsApi)?.let { newsItems.add(it) }
        }
        return newsItems
    }
}