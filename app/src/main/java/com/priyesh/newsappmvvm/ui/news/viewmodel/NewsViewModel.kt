package com.priyesh.newsappmvvm.ui.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.ui.news.repository.NewsRepository
import com.priyesh.newsappmvvm.ui.news.view.NewsUIState
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    private var _newsUIState = MutableLiveData<NewsUIState>(NewsUIState.Loading)
    val newsUIState: LiveData<NewsUIState>
        get() = _newsUIState

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        viewModelScope.launch {
            when(val result = newsRepository.getTopHeadlines("us")) {
                is NetworkResult.FAILURE -> {
                    _newsUIState.value = NewsUIState.Error(result.exception.message ?: "")
                }
                is NetworkResult.SUCCESS -> {
                    _newsUIState.value = NewsUIState.Success(result.data.articles ?: emptyList())
                }
            }
        }
    }
}

class NewsViewModelFactory(private val newsRepository: NewsRepository): ViewModelProvider.Factory {
    @SuppressWarnings("kotlin:S6530")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            NewsViewModel(newsRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}