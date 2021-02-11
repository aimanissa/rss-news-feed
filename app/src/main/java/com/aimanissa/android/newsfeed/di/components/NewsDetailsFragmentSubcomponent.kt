package com.aimanissa.android.newsfeed.di.components

import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsModule
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsFragment
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsPresenter
import dagger.Subcomponent

@Subcomponent(modules = [NewsDetailsModule::class])
interface NewsDetailsFragmentSubcomponent {

    fun inject(newsDetailsFragment: NewsDetailsFragment)

    fun providePresenter(): NewsDetailsPresenter

}