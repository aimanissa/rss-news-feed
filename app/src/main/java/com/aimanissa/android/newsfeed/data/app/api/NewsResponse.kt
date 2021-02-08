package com.aimanissa.android.newsfeed.data.app.api

class NewsResponse(
    var status: String = "",
    var totalResults: Int,
    var articles: List<NewsApiModel>
)