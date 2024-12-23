package com.priyesh.newsappmvvm.di

import com.priyesh.newsappmvvm.ui.news.data.repository.NewsRepositoryImpl
import com.priyesh.newsappmvvm.ui.news.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
fun interface NewsModule {

    @Binds
    fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

}