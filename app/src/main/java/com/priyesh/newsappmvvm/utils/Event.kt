package com.priyesh.newsappmvvm.utils

sealed class Event<T> {
    class Loading<Nothing> : Event<Nothing>()
    data class Success<T>(val data: T) : Event<T>()
    data class Failure<Nothing>(val error: String) : Event<Nothing>()
}