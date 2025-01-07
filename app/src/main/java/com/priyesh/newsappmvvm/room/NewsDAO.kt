package com.priyesh.newsappmvvm.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.priyesh.newsappmvvm.ui.news.domain.model.Article

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<Article>)

    @Query("SELECT * FROM Article")
    suspend fun getArticles(): List<Article>
}