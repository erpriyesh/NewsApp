package com.priyesh.newsappmvvm.network

sealed class NetworkResult<T> {
    data class SUCCESS<T>(val data: T, val message: String? = null) : NetworkResult<T>()
    data class FAILURE<Nothing>(val body: String?, val exception: Exception) :
        NetworkResult<Nothing>()
}
