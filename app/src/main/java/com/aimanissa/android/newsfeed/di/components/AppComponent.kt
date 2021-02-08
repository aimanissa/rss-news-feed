package com.aimanissa.android.newsfeed.di.components

import com.aimanissa.android.newsfeed.di.modules.ApiModule
import com.aimanissa.android.newsfeed.di.modules.AppModule
import com.aimanissa.android.newsfeed.di.modules.DatabaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    fun getMainActivitySubcomponent(): MainActivitySubcomponent

}