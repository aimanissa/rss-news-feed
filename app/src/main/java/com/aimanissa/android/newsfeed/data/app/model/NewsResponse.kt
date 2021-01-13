package com.aimanissa.android.newsfeed.data.app.model

class NewsResponse(
    var status: String = "",
    var totalResults: Int,
    var articles: List<NewsItem>
)