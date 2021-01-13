package com.aimanissa.android.newsfeed.data.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aimanissa.android.newsfeed.data.app.model.NewsItem

@Database(entities = [NewsItem::class], version = 4, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {

        @Volatile
        private var db: NewsDatabase? = null

        private const val DB_NAME = "news.db"
        private val LOCK = Any()

        fun getInstance(context: Context): NewsDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration().build()
                db = instance
                return instance
            }
        }
    }
}