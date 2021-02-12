package com.aimanissa.android.newsfeed.di.components

import androidx.lifecycle.ViewModelProvider
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedModule
import dagger.Subcomponent

@Subcomponent(modules = [NewsFeedModule::class])
interface NewsFeedFragmentSubcomponent {

    fun viewModelFactory(): ViewModelProvider.Factory

}