package com.priyesh.newsappmvvm.ui.news.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.ui.news.data.mappers.ArticleDTOtoArticleMapper
import com.priyesh.newsappmvvm.ui.news.data.mappers.mapAll
import com.priyesh.newsappmvvm.ui.news.data.remote.NewsAPI
import com.priyesh.newsappmvvm.ui.news.domain.model.Article

class NewsPagingSource(
    private val newsAPI: NewsAPI,
    private val category: String?,
    private val mapper: ArticleDTOtoArticleMapper
) :
    PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: 1
            return when (val response = newsAPI.getTopHeadlines(category, position)) {
                is NetworkResult.SUCCESS -> {
                    LoadResult.Page(
                        data = mapper.mapAll(response.data.articles ?: emptyList()),
                        prevKey = if (position == 1) null else position.minus(1),
                        nextKey = if (response.data.articles?.size == 20) position.plus(1) else null
                    )
                }

                is NetworkResult.FAILURE -> {
                    LoadResult.Error(response.exception)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}