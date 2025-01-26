package com.priyesh.newsappmvvm.ui.news.data.mappers

import com.priyesh.newsappmvvm.ui.news.data.model.ArticleDTO
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import javax.inject.Inject

class ArticleDTOtoArticleMapper @Inject constructor(): Mapper<ArticleDTO, Article> {
    override fun map(from: ArticleDTO): Article {
        return Article(
            author = from.author ?: "",
            publishedAt = from.publishedAt ?: "",
            source = from.source?.name ?: "",
            title = from.title ?: "",
            url = from.url ?: "",
            thumbnail = from.urlToImage ?: ""
        )
    }
}