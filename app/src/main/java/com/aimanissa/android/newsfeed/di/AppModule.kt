package com.aimanissa.android.newsfeed.di

import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.adapter.NewsAdapter
import com.aimanissa.android.newsfeed.data.app.api.NewsApi
import com.aimanissa.android.newsfeed.data.app.api.NewsFetchr
import com.aimanissa.android.newsfeed.ui.fragments.interactor.NewsLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): NewsApi = NewsFetchr.getClient()

    @Provides
    fun provideNewsLoader() = NewsLoader()

    @Provides
    fun provideListNews() = ArrayList<NewsItem>()

    @Provides
    fun provideNewsAdapter(newsItems: ArrayList<NewsItem>): NewsAdapter = NewsAdapter(newsItems)

}