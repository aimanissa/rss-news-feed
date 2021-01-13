package com.aimanissa.android.newsfeed.di

import com.aimanissa.android.newsfeed.ui.fragments.NewsFeedFragment
import com.aimanissa.android.newsfeed.ui.fragments.NewsFeedViewModel
import com.aimanissa.android.newsfeed.ui.fragments.interactor.NewsLoader
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(newsLoader: NewsLoader)

    fun inject(viewModel: NewsFeedViewModel)

    fun inject(newsFeedFragment: NewsFeedFragment)

}