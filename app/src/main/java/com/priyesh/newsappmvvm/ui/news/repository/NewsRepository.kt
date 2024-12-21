package com.priyesh.newsappmvvm.ui.news.repository

import com.priyesh.newsappmvvm.network.NetworkConstants
import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.network.RequestHandler
import com.priyesh.newsappmvvm.network.Response
import com.priyesh.newsappmvvm.ui.news.model.Article

class NewsRepository(private val requestHandler: RequestHandler) {
    suspend fun getTopHeadlines(country: String, page: Int = 1): NetworkResult<Response<Article>>{
        return requestHandler.get(
            urlPathSegments = listOf("v2", "top-headlines"),
            queryParams = mapOf("country" to country, "page" to page, "apiKey" to NetworkConstants.API_KEY)
        )
    }
}