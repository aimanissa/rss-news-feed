package com.aimanissa.android.newsfeed

import android.app.Application
import com.aimanissa.android.newsfeed.di.components.AppComponent
import com.aimanissa.android.newsfeed.di.components.DaggerAppComponent
import com.aimanissa.android.newsfeed.di.modules.AppModule

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}