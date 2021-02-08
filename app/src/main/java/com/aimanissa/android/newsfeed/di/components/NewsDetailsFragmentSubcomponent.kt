package com.aimanissa.android.newsfeed.di.components

import androidx.lifecycle.ViewModelProvider
import com.aimanissa.android.newsfeed.di.modules.NewsDetailsModule
import com.aimanissa.android.newsfeed.di.modules.NewsFeedModule
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsFragment
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsViewModel
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(modules = [NewsDetailsModule::class])
interface NewsDetailsFragmentSubcomponent {

    fun viewModelFactory(): ViewModelProvider.Factory

}