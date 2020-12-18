package com.aimanissa.android.newsfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class NewsFeedViewModel : ViewModel() {

    val newsItemLiveData: LiveData<List<NewsItem>>

    init {
        newsItemLiveData = NewsFetchr().fetchNews()
    }
}