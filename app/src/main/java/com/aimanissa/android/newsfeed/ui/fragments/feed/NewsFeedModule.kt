package com.aimanissa.android.newsfeed.ui.fragments.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider


@Module
class NewsFeedModule {

    @Provides
    fun viewModelFactory(newsListProvider: Provider<NewsFeedViewModel>): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return newsListProvider.get() as T
            }
        }
    }
}