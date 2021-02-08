package com.aimanissa.android.newsfeed.ui.fragments.interactor

import com.aimanissa.android.newsfeed.data.app.db.repository.NewsRepositoryImpl
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import io.reactivex.Single
import javax.inject.Inject

class NewsDetailsLoader @Inject constructor(
    private val repository: NewsRepositoryImpl
) {

    fun loadNewsItemByTitle(newsTitle: String): Single<NewsItem> {
        return Single.fromCallable { repository.getNewsByTitle(newsTitle) }
    }

}