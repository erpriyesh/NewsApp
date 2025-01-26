package com.priyesh.newsappmvvm.ui.news.domain.usecase

import com.priyesh.newsappmvvm.ui.news.domain.repository.NewsRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SearchNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(query: String, page: Int = 1) = repository.getSearchedNews(query)
}