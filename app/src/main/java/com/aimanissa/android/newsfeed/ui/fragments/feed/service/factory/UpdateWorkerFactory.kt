package com.aimanissa.android.newsfeed.ui.fragments.feed.service.factory

import android.content.Context
import androidx.work.WorkerParameters
import com.aimanissa.android.newsfeed.ui.fragments.feed.service.UpdateWorker
import dagger.assisted.AssistedFactory

@AssistedFactory
interface UpdateWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): UpdateWorker
}