package com.aimanissa.android.newsfeed.data.app.model

import java.util.*


data class NewsItem(
    var title: String = "",

    var description: String = "",

    var urlToImage: String = "",

    var publishedAt: Date = Date(),
)