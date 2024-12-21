package com.priyesh.newsappmvvm.network

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val articles: MutableList<T>? = null,
    val totalResults: Int? = null,
    val status: String? = null,
    val code: String? = null,
    val message: String? = null,
)