package com.priyesh.newsappmvvm.ui.news.data.remote

import com.priyesh.newsappmvvm.network.NetworkConstants
import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.network.RequestHandler
import com.priyesh.newsappmvvm.ui.news.domain.model.NewsResponse
import javax.inject.Inject

class NewsAPI @Inject constructor(private val requestHandler: RequestHandler) {
    suspend fun getTopHeadlines(): NetworkResult<NewsResponse> = requestHandler.get(listOf("v2", "top-headlines"), mapOf("country" to "us", "page" to 1, "apiKey" to NetworkConstants.API_KEY))
}