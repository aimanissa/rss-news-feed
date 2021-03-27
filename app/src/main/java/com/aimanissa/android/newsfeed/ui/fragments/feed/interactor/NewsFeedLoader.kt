package com.aimanissa.android.newsfeed.ui.fragments.feed.interactor

import com.aimanissa.android.newsfeed.data.app.api.NewsEndpoint
import com.aimanissa.android.newsfeed.data.app.db.repository.NewsRepositoryImpl
import com.aimanissa.android.newsfeed.data.app.mapper.ResponseMapper
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.di.modules.ApiModule.Companion.API_KEY
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class NewsFeedLoader @Inject constructor(
    private val endPoint: NewsEndpoint,
    private val repository: NewsRepositoryImpl,
    private val mapper: ResponseMapper
) {

    fun loadNews(): Single<List<NewsItem>> {
        return endPoint.loadNews(QUERY_COUNTRY, QUERY_SIZE, API_KEY)
            .map { mapper.newsApiListToNewsItemsList(it.articles) }
            .doOnSuccess {
                repository.apply {
                    deleteAll()
                    saveAll(it)
                }
            }
    }

    fun loadNewsFromDb(): Single<List<NewsItem>> {
        return Single.fromCallable { repository.getAll() }
    }

    fun loadSearchNews(query: String): Maybe<List<NewsItem>> {
        return endPoint.searchNews(query, API_KEY)
            .filter { it.totalResults > 0 }
            .map { mapper.newsApiListToNewsItemsList(it.articles) }
            .doOnSuccess {
                repository.apply {
                    deleteAll()
                    saveAll(it)
                }
            }
    }

    fun loadLastNewsItemFromDb(): Single<NewsItem> {
        return Single.fromCallable { repository.getLastNewsItem() }
    }

    companion object {
        private const val QUERY_COUNTRY = "ru"
        private const val QUERY_SIZE = "30"
    }
}

