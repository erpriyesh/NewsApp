package com.priyesh.newsappmvvm

import android.app.Application
import com.priyesh.newsappmvvm.di.AppComponent
import com.priyesh.newsappmvvm.di.DaggerAppComponent

class NewsApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}