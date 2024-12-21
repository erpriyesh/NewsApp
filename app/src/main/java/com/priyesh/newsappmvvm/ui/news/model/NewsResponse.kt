package com.priyesh.newsappmvvm.ui.news.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)