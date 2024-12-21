package com.priyesh.newsappmvvm.ui.news

import com.priyesh.newsappmvvm.ui.news.data.repository.NewsRepositoryImpl
import com.priyesh.newsappmvvm.ui.news.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module

@Module
fun interface NewsModule {

    @Binds
    fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

}