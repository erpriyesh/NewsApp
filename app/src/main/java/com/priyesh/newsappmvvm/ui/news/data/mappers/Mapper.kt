package com.priyesh.newsappmvvm.ui.news.data.mappers

fun interface Mapper<F, T> {
    fun map(from: F): T
}

fun <F, T> Mapper<F, T>.mapAll(list: List<F>) = list.map { map(it) }