package com.priyesh.newsappmvvm.ui.news.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.liveData
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.ui.news.data.model.Category
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.ui.news.domain.usecase.GetNewsUsecase
import com.priyesh.newsappmvvm.ui.news.domain.usecase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUsecase: GetNewsUsecase,
    private val searchNewsUseCase: SearchNewsUseCase,
) : ViewModel() {

    private val _news = MutableLiveData<PagingData<Article>>()
    val news: LiveData<PagingData<Article>> = _news

    private val _searchedNews = MutableLiveData<PagingData<Article>>()
    val searchedNews: LiveData<PagingData<Article>> = _searchedNews

    fun loadNews(category: String? = null) {
        val pagingLiveData = newsUsecase.invoke(category).liveData.cachedIn(viewModelScope)
        pagingLiveData.observeForever { pagingData ->
            _news.postValue(pagingData.filter { !it.title.contains("Removed", true) })
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

    fun getSearchedNews(query: String) {
        val pagingLiveData = searchNewsUseCase.invoke(query).liveData.cachedIn(viewModelScope)
        pagingLiveData.observeForever { pagingData ->
            _searchedNews.postValue(pagingData.filter { !it.title.contains("Removed", true) })
        }
    }
}