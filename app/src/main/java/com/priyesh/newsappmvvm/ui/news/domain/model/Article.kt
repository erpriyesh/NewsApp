package com.priyesh.newsappmvvm.ui.news.domain.model

data class Article(
    val author: String?,
    val publishedAt: String?,
    val source: String?,
    val title: String = "",
    val url: String?,
    val thumbnail: String?
)