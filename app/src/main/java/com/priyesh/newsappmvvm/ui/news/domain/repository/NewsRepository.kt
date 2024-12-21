package com.priyesh.newsappmvvm.ui.news.domain.repository

import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.ui.news.domain.model.NewsResponse

fun interface NewsRepository {
    suspend fun getTopHeadlines(): NetworkResult<NewsResponse>
}