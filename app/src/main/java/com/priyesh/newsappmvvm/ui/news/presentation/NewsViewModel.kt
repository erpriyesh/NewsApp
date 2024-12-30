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
import com.priyesh.newsappmvvm.ui.news.domain.usecase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUsecase: GetNewsUsecase,
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> = _news

    private val _searchedNews = MutableLiveData<List<Article>>()
    val searchedNews: LiveData<List<Article>> = _searchedNews

    init {
        loadNews()
    }

    fun loadNews(category: String? = null) {
        viewModelScope.launch {
            when (val response = newsUsecase(category)) {
                is NetworkResult.SUCCESS -> _news.value =
                    response.data.articles?.filter { it.title?.contains("Removed", true) != true }
                        ?: emptyList()

                is NetworkResult.FAILURE -> Log.e(
                    "NewsViewModel",
                    response.exception.message.toString()
                )
            }
        }
    }

    fun getCategoryList(): List<Category> {
        return mutableListOf(
            Category("All", null, R.drawable.black_gradient, true),
            Category("Business", "business", R.drawable.business, false),
            Category("Entertainment", "entertainment", R.drawable.entertainment, false),
            Category("Health", "health", R.drawable.health, false),
            Category("Science", "science", R.drawable.science, false),
            Category("Sports", "sports", R.drawable.sports, false),
            Category("Technology", "technology", R.drawable.technology, false)
        )
    }

    fun searchNews(query: String, page: Int = 1): LiveData<List<Article>> {
        viewModelScope.launch {
            when (val response = searchNewsUseCase(query, page)) {
                is NetworkResult.FAILURE -> Log.e(
                    "NewsViewModel",
                    response.exception.message.toString()
                )

                is NetworkResult.SUCCESS -> _searchedNews.value =
                    response.data.articles?.filter { it.title?.contains("Removed", true) != true }
                        ?: emptyList()
            }
        }
        return searchedNews
    }
}