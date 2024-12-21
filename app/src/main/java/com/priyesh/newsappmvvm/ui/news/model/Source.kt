package com.priyesh.newsappmvvm.ui.news.model

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String?
)