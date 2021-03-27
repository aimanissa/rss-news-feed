package com.aimanissa.android.newsfeed

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Configuration
import androidx.work.WorkManager
import com.aimanissa.android.newsfeed.di.components.AppComponent
import com.aimanissa.android.newsfeed.di.components.DaggerAppComponent
import com.aimanissa.android.newsfeed.di.modules.AppModule

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
        initWorkManager()
        initNotifications()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }

    private fun initWorkManager() {
        val factory = appComponent.getMainActivitySubcomponent()
            .newsFeedComponent().workerFactory()
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(factory)
                .build()
        )
    }

    private fun initNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "news_feed_update"

        lateinit var appComponent: AppComponent
    }
}