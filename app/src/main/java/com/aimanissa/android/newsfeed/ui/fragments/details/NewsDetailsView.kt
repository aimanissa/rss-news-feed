package com.aimanissa.android.newsfeed.ui.fragments.details

import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface NewsDetailsView : MvpView {
    fun setItem(item: NewsItem)
    fun updateUI()
}