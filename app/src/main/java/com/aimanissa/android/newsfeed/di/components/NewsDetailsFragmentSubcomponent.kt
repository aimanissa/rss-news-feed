package com.aimanissa.android.newsfeed.di.components

import com.aimanissa.android.newsfeed.di.modules.NewsDetailsModule
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsFragment
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsPresenter
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedFragment
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedPresenter
import dagger.Subcomponent

@Subcomponent(modules = [NewsDetailsModule::class])
interface NewsDetailsFragmentSubcomponent {

    fun inject(newsDetailsFragment: NewsDetailsFragment)

    fun providePresenter(): NewsDetailsPresenter

}