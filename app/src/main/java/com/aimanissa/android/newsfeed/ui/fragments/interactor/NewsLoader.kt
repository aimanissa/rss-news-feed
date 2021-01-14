package com.aimanissa.android.newsfeed.ui.fragments.interactor

import android.util.Log
import com.aimanissa.android.newsfeed.NewsApplication
import com.aimanissa.android.newsfeed.data.app.api.NewsApi
import com.aimanissa.android.newsfeed.data.app.api.NewsFetchr.API_KEY
import com.aimanissa.android.newsfeed.di.DaggerAppComponent
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsLoader {

    @Inject
    lateinit var newsFetchr: NewsApi

    val newsList = NewsApplication.database.newsDao().getNewsList()

    init {
        DaggerAppComponent.create().inject(this)
    }

    private fun loadNews(): Disposable {
        return newsFetchr.fetchNews(QUERY_COUNTRY, QUERY_CATEGORY, QUERY_SIZE, API_KEY)
            .map { it.articles }
            .subscribeOn(Schedulers.io())
            .subscribe({
                NewsApplication.database.apply {
                    clearAllTables()
                    newsDao().insertNewsList(it)
                }
            }, {
                Log.e(TAG, "NewsResponse error: ${it?.message}")
            })
    }

    fun fetchNews(): Disposable = loadNews()

    companion object {
        private const val TAG = "NewsLoader"

        private const val QUERY_COUNTRY = "ru"
        private const val QUERY_CATEGORY = "technology"
        private const val QUERY_SIZE = "20"
    }
}

