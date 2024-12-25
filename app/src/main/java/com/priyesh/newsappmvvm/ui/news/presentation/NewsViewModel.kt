package com.priyesh.newsappmvvm.ui.news.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.ui.news.domain.model.Category
import com.priyesh.newsappmvvm.ui.news.domain.usecase.GetNewsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUsecase: GetNewsUsecase) : ViewModel() {
    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> = _news

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            when (val response = newsUsecase()) {
                is NetworkResult.SUCCESS -> _news.value = response.data.articles ?: emptyList()
                is NetworkResult.FAILURE -> Log.e(
                    "NewsViewModel",
                    response.exception.message.toString()
                )
            }
        }
    }

    fun getCategoryList(): List<Category> {
        return mutableListOf(
            Category("All", R.drawable.black_gradient, true),
            Category("Business", R.drawable.business, false),
            Category("Entertainment", R.drawable.entertainment, false),
            Category("Health", R.drawable.health, false),
            Category("Science", R.drawable.science, false),
            Category("Sports", R.drawable.sports, false),
            Category("Technology", R.drawable.technology, false)
        )
    }
}