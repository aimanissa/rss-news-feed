package com.aimanissa.android.newsfeed.di.components

import com.aimanissa.android.newsfeed.di.modules.NewsDetailsModule
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsPresenter
import dagger.Subcomponent

@Subcomponent(modules = [NewsDetailsModule::class])
interface NewsDetailsFragmentSubcomponent {

    fun inject(newsDetailsPresenter: NewsDetailsPresenter)

}