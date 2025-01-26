package com.priyesh.newsappmvvm.ui.news.data.model

import androidx.annotation.DrawableRes

data class Category(
    val categoryName: String,
    val category: String? = null,
    @DrawableRes val categoryBackground: Int,
    var isCategorySelected: Boolean = false
)
