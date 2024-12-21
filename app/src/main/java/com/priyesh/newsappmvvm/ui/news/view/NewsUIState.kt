package com.priyesh.newsappmvvm.ui.news.view

import com.priyesh.newsappmvvm.ui.news.model.Article

sealed class NewsUIState {
    data object Loading : NewsUIState()
    data class Success(val result: List<Article>) : NewsUIState()
    data class Error(val message: String) : NewsUIState()
}