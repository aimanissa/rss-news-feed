package com.aimanissa.android.newsfeed.di.components

import androidx.lifecycle.ViewModelProvider
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsModule
import dagger.Subcomponent

@Subcomponent(modules = [NewsDetailsModule::class])
interface NewsDetailsFragmentSubcomponent {

    fun viewModelFactory(): ViewModelProvider.Factory

}