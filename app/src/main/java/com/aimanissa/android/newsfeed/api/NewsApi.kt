package com.aimanissa.android.newsfeed.api

import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {

    @GET(
        "top-headlines?country=ru" +
                "&category=technology" +
                "&pageSize=40" +
                "&apiKey=770940cf56c84fd8be89cccaeb14cc33"
    )
    fun fetchNews(): Call<NewsResponse>
}