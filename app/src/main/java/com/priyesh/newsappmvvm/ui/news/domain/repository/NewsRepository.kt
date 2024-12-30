package com.priyesh.newsappmvvm.ui.news.domain.repository

import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.ui.news.domain.model.NewsResponse

interface NewsRepository {
    suspend fun getTopHeadlines(category: String?): NetworkResult<NewsResponse>
    suspend fun searchNews(query: String, page: Int): NetworkResult<NewsResponse>
}