package com.priyesh.newsappmvvm.ui.news.presentation.news_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.LayoutItemCategoryAdapterBinding
import com.priyesh.newsappmvvm.ui.news.domain.model.Category

class NewsCategoryAdapter(private val onCategorySelected: (Category) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var categoryList: List<Category>? = null

    fun submitList(list: List<Category>) {
        categoryList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutItemCategoryAdapterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_category_adapter,
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        categoryList?.get(position)?.let { (holder as CategoryViewHolder).bindView(it) }
    }

    inner class CategoryViewHolder(private val binding: LayoutItemCategoryAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(category: Category) {
            binding.category.text = category.categoryName
            binding.background.setImageDrawable(
                AppCompatResources.getDrawable(
                    binding.root.context,
                    category.categoryBackground
                )
            )
            binding.card.strokeWidth = if (category.isCategorySelected) 2 else 0
            itemView.setOnClickListener {
                categoryList?.forEach { it.isCategorySelected = false }
                categoryList?.get(absoluteAdapterPosition)?.isCategorySelected = true
                notifyDataSetChanged()
                onCategorySelected.invoke(category)
            }
        }
    }
}