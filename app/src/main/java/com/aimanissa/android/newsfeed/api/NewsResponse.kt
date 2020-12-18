package com.aimanissa.android.newsfeed.api

import com.aimanissa.android.newsfeed.NewsItem

class NewsResponse(
    var status: String = "",
    var totalResults: Int,
    var articles: List<NewsItem>
)