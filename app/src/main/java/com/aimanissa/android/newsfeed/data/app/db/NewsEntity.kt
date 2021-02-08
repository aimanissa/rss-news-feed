package com.aimanissa.android.newsfeed.data.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_headlines")
class NewsEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "title")
    var title: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "urlToImage")
    var urlToImage: String = ""
}

