package com.priyesh.newsappmvvm.ui.news.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @Embedded(prefix = "source_")
    val source: Source?,
    @PrimaryKey
    val title: String = "",
    val url: String?,
    val urlToImage: String?
)