package com.aimanissa.android.newsfeed.ui.fragments.feed

import android.util.Log
import androidx.lifecycle.*
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.ui.fragments.feed.interactor.NewsFeedLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private var loader: NewsFeedLoader
) : ViewModel(), LifecycleObserver {

    private var loadDisposable: Disposable? = null

    val loadedNews = MutableLiveData<List<NewsItem>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initViewModel() {
        initData()
    }

    fun onRefresh() {
        loadNews()
    }

    private fun initData() {
        loadNewsFromDb()
    }

    private fun loadNews() {
        loadDisposable?.dispose()
        loadDisposable = loader.loadNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadedNews.value = it
            }, {
                Log.e(TAG, "loadNews() error: ${it?.message}")
            })
    }

    private fun loadNewsFromDb() {
        loadDisposable?.dispose()
        loadDisposable = loader.loadNewsFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadedNews.value = it
            }, {
                Log.e(TAG, "loadNewsFromDb() error: ${it?.message}")
            })
    }

    fun loadSearchNews(query: String) {
        loadDisposable?.dispose()
        loadDisposable = loader.loadSearchNews(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadedNews.value = it
            }, {
                Log.e(TAG, "loadSearchNews() error: ${it?.message}")
            })
    }

    override fun onCleared() {
        super.onCleared()
        loadDisposable?.dispose()
    }

    companion object {
        private const val TAG = "NewsFeedViewModel"
    }
}