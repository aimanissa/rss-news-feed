package com.aimanissa.android.newsfeed.di.components

import dagger.Subcomponent

@Subcomponent
interface MainActivitySubcomponent {

    fun newsFeedComponent(): NewsFeedFragmentSubcomponent

    fun newsDetailsComponent(): NewsDetailsFragmentSubcomponent
}