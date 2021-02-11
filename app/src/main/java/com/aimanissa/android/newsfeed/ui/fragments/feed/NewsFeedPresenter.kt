package com.aimanissa.android.newsfeed.ui.fragments.feed

import android.util.Log
import com.aimanissa.android.newsfeed.ui.fragments.feed.interactor.NewsFeedLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NewsFeedPresenter @Inject constructor(
    private val loader: NewsFeedLoader
) : MvpPresenter<NewsFeedView>() {

    private var loadDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
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
                viewState.setItems(it)
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
                viewState.setItems(it)
            }, {
                Log.e(TAG, "loadNewsFromDb() error: ${it?.message}")
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        loadDisposable?.dispose()
    }

    companion object {
        private const val TAG = "NewsFeedPresenter"
    }

}