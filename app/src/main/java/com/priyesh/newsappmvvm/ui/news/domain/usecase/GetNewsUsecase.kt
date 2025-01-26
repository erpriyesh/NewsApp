package com.priyesh.newsappmvvm.ui.news.domain.usecase

import com.priyesh.newsappmvvm.ui.news.domain.repository.NewsRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetNewsUsecase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(category: String?) = newsRepository.getPagedTopHeadlines(category)
}