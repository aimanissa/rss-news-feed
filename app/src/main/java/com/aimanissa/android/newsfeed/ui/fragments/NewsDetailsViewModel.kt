package com.aimanissa.android.newsfeed.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aimanissa.android.newsfeed.data.app.model.NewsItem

class NewsDetailsViewModel : ViewModel() {

    private val _selectedNewsItem = MutableLiveData<NewsItem>()
    val selectedNewsItem: LiveData<NewsItem>
        get() = _selectedNewsItem

    fun setSelectedNewsItem(newsItem: NewsItem) {
        _selectedNewsItem.postValue(newsItem)
    }

}