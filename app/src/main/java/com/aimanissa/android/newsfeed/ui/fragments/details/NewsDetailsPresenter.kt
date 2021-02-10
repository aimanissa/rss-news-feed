package com.aimanissa.android.newsfeed.ui.fragments.details

import android.util.Log
import com.aimanissa.android.newsfeed.NewsApplication
import com.aimanissa.android.newsfeed.ui.fragments.interactor.NewsDetailsLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NewsDetailsPresenter @Inject constructor(
    private val loader: NewsDetailsLoader
) : MvpPresenter<NewsDetailsView>() {

    private var loadDisposable: Disposable? = null
    lateinit var selectedNewsTitle: String

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initData()
    }

    private fun initData() {
        loadNewsItemByTitle()
    }

    private fun loadNewsItemByTitle() {
        loadDisposable?.dispose()
        loadDisposable = loader.loadNewsItemByTitle(selectedNewsTitle)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.apply {
                    setItem(it)
                    updateUI()
                }
            }, {
                Log.e(TAG, "loadNewsItemByTitle() error: ${it?.message}")
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        loadDisposable?.dispose()
    }

    companion object {
        private const val TAG = "NewsDetailsPresenter"
    }
}