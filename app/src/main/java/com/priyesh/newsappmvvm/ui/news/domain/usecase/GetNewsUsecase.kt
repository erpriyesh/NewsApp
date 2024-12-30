package com.priyesh.newsappmvvm.ui.news.domain.usecase

import com.priyesh.newsappmvvm.network.NetworkResult
import com.priyesh.newsappmvvm.ui.news.domain.model.NewsResponse
import com.priyesh.newsappmvvm.ui.news.domain.repository.NewsRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetNewsUsecase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(category: String?): NetworkResult<NewsResponse> =
        newsRepository.getTopHeadlines(category)
}