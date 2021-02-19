package com.aimanissa.android.newsfeed.data.app.api

import com.google.gson.annotations.SerializedName

data class NewsApiModel (
    @SerializedName("title")
    val title: String? = "",

    @SerializedName("description")
    val description: String? = "",

    @SerializedName("urlToImage")
    val urlToImage: String? = "",

    @SerializedName("publishedAt")
    val publishedAt: String? = ""
)