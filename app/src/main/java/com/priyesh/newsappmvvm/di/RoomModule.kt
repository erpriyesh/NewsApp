package com.priyesh.newsappmvvm.di

import android.content.Context
import com.priyesh.newsappmvvm.room.NewsDAO
import com.priyesh.newsappmvvm.room.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideNewsDAO(newsDatabase: NewsDatabase): NewsDAO {
        return newsDatabase.newsDao()
    }
}