package com.aimanissa.android.newsfeed.ui.fragments.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class NewsDetailsModule {

    @Provides
    fun viewModelFactory(newsListProvider: Provider<NewsDetailsViewModel>): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return newsListProvider.get() as T
            }
        }
    }
}