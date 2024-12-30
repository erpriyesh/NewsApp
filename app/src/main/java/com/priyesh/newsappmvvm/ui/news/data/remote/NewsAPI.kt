package com.priyesh.newsappmvvm.ui.news.data.remote

import com.priyesh.newsappmvvm.network.NetworkConstants
import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.network.RequestHandler
import com.priyesh.newsappmvvm.ui.news.domain.model.NewsResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class NewsAPI @Inject constructor(private val requestHandler: RequestHandler) {

    suspend fun getTopHeadlines(category: String?): NetworkResult<NewsResponse> {
        val map = if (!category.isNullOrEmpty()) mapOf(
            "country" to "us",
            "category" to category,
            "page" to 1,
            "apiKey" to NetworkConstants.API_KEY
        ) else mapOf("country" to "us", "page" to 1, "apiKey" to NetworkConstants.API_KEY)
        return requestHandler.get(listOf("v2", "top-headlines"), map)
    }

    suspend fun searchNews(query: String, page: Int): NetworkResult<NewsResponse> =
        requestHandler.get(
            listOf("v2", "everything"),
            mapOf("q" to query, "page" to page, "apiKey" to NetworkConstants.API_KEY)
        )
}