package com.aimanissa.android.newsfeed

import android.app.Application
import com.aimanissa.android.newsfeed.data.app.db.NewsDatabase

class NewsApplication : Application() {

    companion object {
        lateinit var instance: NewsApplication
        lateinit var database: NewsDatabase
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = NewsDatabase.getInstance(this)
    }
}