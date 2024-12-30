package com.priyesh.newsappmvvm.ui.news.data.repository

import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.ui.news.data.remote.NewsAPI
import com.priyesh.newsappmvvm.ui.news.domain.model.NewsResponse
import com.priyesh.newsappmvvm.ui.news.domain.repository.NewsRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class NewsRepositoryImpl @Inject constructor(private val api: NewsAPI) : NewsRepository {
    override suspend fun getTopHeadlines(category: String?): NetworkResult<NewsResponse> =
        api.getTopHeadlines(category)

    override suspend fun searchNews(query: String, page: Int): NetworkResult<NewsResponse> =
        api.searchNews(query, page)
}