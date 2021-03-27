package com.aimanissa.android.newsfeed.ui.fragments.feed.service

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aimanissa.android.newsfeed.NewsApplication.Companion.NOTIFICATION_CHANNEL_ID
import com.aimanissa.android.newsfeed.R
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.ui.activity.MainActivity
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedFragment.Companion.PREF_SEARCH_QUERY
import com.aimanissa.android.newsfeed.ui.fragments.feed.interactor.NewsFeedLoader
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.disposables.Disposable

class UpdateWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted params: WorkerParameters,
    private val loader: NewsFeedLoader
) : Worker(appContext, params) {

    private var loadDisposable: Disposable? = null
    private var lastDatabaseItem = NewsItem()
    private var newsItems: List<NewsItem> = mutableListOf()

    override fun doWork(): Result {
        loadLastNewsItemFromDb()
        val savedQuery = getSavedQuery()

        if (savedQuery.isEmpty()) {
            loadNews()
        } else {
            loadSearchNews(savedQuery)
        }

        val lastRemoteItem = newsItems.last()
        if (lastRemoteItem.title == lastDatabaseItem.title) {
            Log.d(TAG, "Got an old result: $lastRemoteItem")
        } else {
            Log.d(TAG, "Got a new result: $lastRemoteItem")

            val intent = MainActivity.newIntent(appContext)
            val pendingIntent =
                PendingIntent.getActivity(appContext, 0, intent, 0)

            val resources = appContext.resources
            val notification = NotificationCompat
                .Builder(appContext, NOTIFICATION_CHANNEL_ID)
                .setTicker(resources.getString(R.string.new_news_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.new_news_title))
                .setContentText(resources.getString(R.string.new_news_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(appContext).notify(0, notification)
        }

        loadDisposable?.dispose()
        return Result.success()
    }

    private fun loadLastNewsItemFromDb() {
        loadDisposable?.dispose()
        loadDisposable = loader.loadLastNewsItemFromDb()
            .subscribe({
                lastDatabaseItem = it
                Log.d(TAG, "loadLastNewsItemFromDb() success: $lastDatabaseItem")
            }, {
                Log.e(TAG, "loadLastNewsItemFromDb() error: ${it?.message}")
            })
    }

    private fun loadNews() {
        loadDisposable?.dispose()
        loadDisposable = loader.loadNews()
            .subscribe({
                newsItems = it
            }, {
                Log.e(TAG, "loadNews() error: ${it?.message}")
            })
    }

    private fun loadSearchNews(query: String) {
        loadDisposable?.dispose()
        loadDisposable = loader.loadSearchNews(query)
            .subscribe({
                newsItems = it
            }, {
                Log.e(TAG, "loadSearchNews() error: ${it?.message}")
            })
    }

    private fun getSavedQuery(): String {
        return PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(PREF_SEARCH_QUERY, "")!!
    }

    companion object {
        private const val TAG = "UpdateWorker"
    }
}