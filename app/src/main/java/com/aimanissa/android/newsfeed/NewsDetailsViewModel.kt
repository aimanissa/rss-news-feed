package com.aimanissa.android.newsfeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class NewsDetailsViewModel : ViewModel() {

    val selectedNewsItem = MutableLiveData<NewsItem>()

    fun setSelectedNewsItem(newsItem: NewsItem) {
        selectedNewsItem.value = newsItem
    }


}