package com.priyesh.newsappmvvm.ui.news.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)