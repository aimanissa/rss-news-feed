package com.aimanissa.android.newsfeed.ui.fragments.feed

import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface NewsFeedView : MvpView {
    fun setItems(items: List<NewsItem>)
}