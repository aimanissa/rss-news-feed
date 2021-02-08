package com.aimanissa.android.newsfeed.di.modules

import android.content.Context
import androidx.room.Room
import com.aimanissa.android.newsfeed.data.app.db.NewsDao
import com.aimanissa.android.newsfeed.data.app.db.NewsDatabase
import com.aimanissa.android.newsfeed.data.app.mapper.DatabaseMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao = database.newsDao()

    @Singleton
    @Provides
    fun provideMapper(): DatabaseMapper = DatabaseMapper()

    companion object {
        private const val DB_NAME = "news.db"
    }

}