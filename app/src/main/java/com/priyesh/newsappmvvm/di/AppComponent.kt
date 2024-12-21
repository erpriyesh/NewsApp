package com.priyesh.newsappmvvm.di

import android.content.Context
import com.priyesh.newsappmvvm.ui.news.view.NewsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
fun interface AppComponent {

    fun inject(activity: NewsActivity)

    @Component.Factory
    fun interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}