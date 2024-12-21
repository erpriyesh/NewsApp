package com.priyesh.newsappmvvm.di

import android.content.Context
import com.priyesh.newsappmvvm.ui.news.NewsModule
import com.priyesh.newsappmvvm.ui.news.presentation.NewsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, NewsModule::class, ViewModelModule::class])
fun interface AppComponent {

    fun inject(newsActivity: NewsActivity)

    @Component.Factory
    fun interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}