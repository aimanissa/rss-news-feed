package com.aimanissa.android.newsfeed.ui.fragments.feed.service

import com.aimanissa.android.newsfeed.ui.fragments.feed.service.factory.UpdateWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(UpdateWorker::class)
    fun bindUpdateWorker(updateWorkerFactory: UpdateWorkerFactory): UpdateWorkerFactory
}