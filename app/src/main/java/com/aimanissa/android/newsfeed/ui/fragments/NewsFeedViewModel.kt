package com.aimanissa.android.newsfeed.ui.fragments

import androidx.lifecycle.ViewModel
import com.aimanissa.android.newsfeed.di.DaggerAppComponent
import com.aimanissa.android.newsfeed.ui.fragments.interactor.NewsLoader
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsFeedViewModel : ViewModel() {

    @Inject
    lateinit var newsLoader: NewsLoader

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        DaggerAppComponent.create().inject(this)
        compositeDisposable.add(newsLoader.fetchNews())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}