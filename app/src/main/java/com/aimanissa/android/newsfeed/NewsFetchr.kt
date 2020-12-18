package com.aimanissa.android.newsfeed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aimanissa.android.newsfeed.api.NewsApi
import com.aimanissa.android.newsfeed.api.NewsResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NewsFetchr {

    private val newsApi: NewsApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsApi = retrofit.create(NewsApi::class.java)
    }

    fun fetchNews(): LiveData<List<NewsItem>> {
        val responseLiveData: MutableLiveData<List<NewsItem>> = MutableLiveData()
        val newsRequest: Call<NewsResponse> = newsApi.fetchNews()

        newsRequest.enqueue(object : Callback<NewsResponse> {

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch news", t)
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                Log.d(TAG, "Response received")
                val newsResponse: NewsResponse? = response.body()
                val newsItems: List<NewsItem> = newsResponse?.articles ?: mutableListOf()
                responseLiveData.value = newsItems
            }
        })
        return responseLiveData
    }

    companion object {
        private const val TAG = "NewsFetchr"
    }
}