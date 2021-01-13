package com.aimanissa.android.newsfeed.data.app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aimanissa.android.newsfeed.data.app.model.NewsItem

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsList(newsList: List<NewsItem>)

    @Query("SELECT * FROM news_headlines")
    fun getNewsList(): LiveData<List<NewsItem>>

    @Update
    fun updateNewsList(newsList: List<NewsItem>)
}