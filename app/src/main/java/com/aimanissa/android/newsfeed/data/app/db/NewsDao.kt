package com.aimanissa.android.newsfeed.data.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newsList: List<NewsEntity?>)

    @Query("SELECT * FROM news_headlines")
    fun getAll(): List<NewsEntity>

    @Query("SELECT * FROM news_headlines WHERE title=(:newsTitle)")
    fun getNewsByTitle(newsTitle: String): NewsEntity

    @Query("SELECT * FROM news_headlines ORDER BY id DESC LIMIT 1")
    fun getLastNewsItem(): NewsEntity

    @Query("DELETE FROM news_headlines")
    fun deleteAll()
}