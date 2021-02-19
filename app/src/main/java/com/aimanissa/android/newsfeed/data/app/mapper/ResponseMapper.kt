package com.aimanissa.android.newsfeed.data.app.mapper


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

    private fun newsApiDateToNewsItemDate(apiDate: String?): Date? {
        return when (apiDate) {
            PATTERN_ONE -> SimpleDateFormat(PATTERN_ONE, Locale.ENGLISH).parse(apiDate)
            PATTERN_TWO -> SimpleDateFormat(PATTERN_TWO, Locale.ENGLISH).parse(apiDate)
            else -> null
        }
    }

    companion object {
        private const val PATTERN_ONE = "yyyy-MM-dd'T'HH:mm:SS'Z'"
        private const val PATTERN_TWO = "yyyy-MM-dd'T'HH:mm:SS+HH:mm"
    }
}