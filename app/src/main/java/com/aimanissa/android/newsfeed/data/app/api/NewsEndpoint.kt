package com.aimanissa.android.newsfeed.data.app.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsEndpoint {

    @GET("top-headlines")
    fun loadNews(
        @Query("country") country: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String
    ): Single<NewsResponse>

    @GET("top-headlines")
    fun searchNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Single<NewsResponse>
}