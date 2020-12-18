package com.aimanissa.android.newsfeed

import android.os.Parcelable
import com.aimanissa.android.newsfeed.api.SourceResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    var title: String = "",
    var description: String = "",
    var urlToImage: String = ""
) : Parcelable