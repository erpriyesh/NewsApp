package com.priyesh.newsappmvvm.ui.news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<ArticleDTO>?,
    val status: String?,
    val totalResults: Int?
)