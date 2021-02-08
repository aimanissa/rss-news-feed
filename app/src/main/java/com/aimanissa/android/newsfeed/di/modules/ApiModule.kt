package com.aimanissa.android.newsfeed.di.modules

import com.aimanissa.android.newsfeed.data.app.api.NewsEndpoint
import com.aimanissa.android.newsfeed.data.app.mapper.ResponseMapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsEndpoint {
        return retrofit.create(NewsEndpoint::class.java)
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideMapper(): ResponseMapper = ResponseMapper()

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "770940cf56c84fd8be89cccaeb14cc33"
        private const val TIMEOUT_IN_SECONDS = 30L
    }
}