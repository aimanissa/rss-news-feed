package com.aimanissa.android.newsfeed.di.components

import com.aimanissa.android.newsfeed.di.modules.NewsFeedModule
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedFragment
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedPresenter
import dagger.Subcomponent

@Subcomponent(modules = [NewsFeedModule::class])
interface NewsFeedFragmentSubcomponent {

    fun inject(newsFeedFragment: NewsFeedFragment)

    fun providePresenter(): NewsFeedPresenter

}