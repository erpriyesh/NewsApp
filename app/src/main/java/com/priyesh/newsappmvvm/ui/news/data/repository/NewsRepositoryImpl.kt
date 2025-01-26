package com.priyesh.newsappmvvm.ui.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.priyesh.newsappmvvm.ui.news.data.mappers.ArticleDTOtoArticleMapper
import com.priyesh.newsappmvvm.ui.news.data.paging.NewsPagingSource
import com.priyesh.newsappmvvm.ui.news.data.paging.SearchNewsPagingSource
import com.priyesh.newsappmvvm.ui.news.data.remote.NewsAPI
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.ui.news.domain.repository.NewsRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsAPI,
    private val mapper: ArticleDTOtoArticleMapper
) : NewsRepository {

    override fun getPagedTopHeadlines(category: String?): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = { NewsPagingSource(api, category, mapper) }
        )
    }

    override fun getSearchedNews(query: String): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = { SearchNewsPagingSource(api, query, mapper) }
        )
    }
}