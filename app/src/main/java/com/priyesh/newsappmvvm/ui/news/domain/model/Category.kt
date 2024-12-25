package com.priyesh.newsappmvvm.ui.news.domain.model

import androidx.annotation.DrawableRes

data class Category(
    val categoryName: String,
    @DrawableRes val categoryBackground: Int,
    var isCategorySelected: Boolean = false
)
