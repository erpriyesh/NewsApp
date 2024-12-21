package com.priyesh.newsappmvvm.ui.news.data.repository

import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.ui.news.data.remote.NewsAPI
import com.priyesh.newsappmvvm.ui.news.domain.model.NewsResponse
import com.priyesh.newsappmvvm.ui.news.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api: NewsAPI) : NewsRepository {
    override suspend fun getTopHeadlines(): NetworkResult<NewsResponse> = api.getTopHeadlines()
}