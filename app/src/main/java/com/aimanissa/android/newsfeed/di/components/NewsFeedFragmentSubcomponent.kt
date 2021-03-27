package com.aimanissa.android.newsfeed.di.components

import androidx.lifecycle.ViewModelProvider
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedModule
import com.aimanissa.android.newsfeed.ui.fragments.feed.service.factory.AppWorkerFactory
import com.aimanissa.android.newsfeed.ui.fragments.feed.service.WorkerModule
import dagger.Subcomponent

@Subcomponent(modules = [NewsFeedModule::class, WorkerModule::class])
interface NewsFeedFragmentSubcomponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    fun workerFactory(): AppWorkerFactory
}