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
    private val loader: NewsFeedLoader
) : ViewModel(), LifecycleObserver {

    private var loadDisposable: Disposable? = null

    val loadedNews = MutableLiveData<List<NewsItem>>()
    val isNewsFound = MutableLiveData<Boolean>()
    val isLoadError = MutableLiveData<Boolean>()

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

    fun loadNews() {
        loadDisposable?.dispose()
        loadDisposable = loader.loadNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadedNews.value = it
                isNewsFound.value = true
                isLoadError.value = false
            }, {
                Log.e(TAG, "loadNews() error: ${it?.message}")
                isLoadError.value = true
            })
    }

    fun loadNewsFromDb() {
        loadDisposable?.dispose()
        loadDisposable = loader.loadNewsFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadedNews.value = it
                isNewsFound.value = true
                isLoadError.value = false
            }, {
                Log.e(TAG, "loadNewsFromDb() error: ${it?.message}")
                isLoadError.value = true
            })
    }

    fun loadSearchNews(query: String) {
        loadDisposable?.dispose()
        loadDisposable = loader.loadSearchNews(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadedNews.value = it
                isNewsFound.value = true
                isLoadError.value = false
            }, {
                Log.e(TAG, "loadSearchNews() error: ${it?.message}")
                isLoadError.value = true
            }, {
                loadedNews.value = emptyList()
                isNewsFound.value = false
                isLoadError.value = false
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