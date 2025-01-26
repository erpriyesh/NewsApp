package com.priyesh.newsappmvvm.ui.news.domain.repository

import androidx.paging.Pager
import com.priyesh.newsappmvvm.ui.news.domain.model.Article

interface NewsRepository {
    fun getPagedTopHeadlines(category: String?): Pager<Int, Article>
    fun getSearchedNews(query: String): Pager<Int, Article>
}