package com.aimanissa.android.newsfeed.data.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aimanissa.android.newsfeed.data.app.mapper.DatabaseMapper

@Database(entities = [NewsEntity::class], version = 12, exportSchema = false)
@TypeConverters(DatabaseMapper::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}