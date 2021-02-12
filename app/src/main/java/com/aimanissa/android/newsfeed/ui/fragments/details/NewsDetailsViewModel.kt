package com.aimanissa.android.newsfeed.ui.fragments.details

import android.util.Log
import androidx.lifecycle.*
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.ui.fragments.details.interactor.NewsDetailsLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsDetailsViewModel @Inject constructor(
    private val loader: NewsDetailsLoader
) : ViewModel(), LifecycleObserver {

    private var loadDisposable: Disposable? = null

    private lateinit var selectedNewsTitle: String

    var loadedNewsItem = MutableLiveData<NewsItem>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initViewModel() {
        initData()
    }

    private fun initData() {
        loadNewsItemByTitle()
    }

    fun setSelectedNewsTitle(newsTitle: String) {
        selectedNewsTitle = newsTitle
    }

    private fun loadNewsItemByTitle() {
        loadDisposable?.dispose()
        loadDisposable = loader.loadNewsItemByTitle(selectedNewsTitle)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadedNewsItem.value = it
            }, {
                Log.e(TAG, "loadNewsItemByTitle() error: ${it?.message}")
            })
    }

    override fun onCleared() {
        super.onCleared()
        loadDisposable?.dispose()
    }

    companion object {
        private const val TAG = "NewsDetailsViewModel"
    }
}