package com.aimanissa.android.newsfeed.data.app.api

import com.aimanissa.android.newsfeed.data.app.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun fetchNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String
    ): Single<NewsResponse>
}