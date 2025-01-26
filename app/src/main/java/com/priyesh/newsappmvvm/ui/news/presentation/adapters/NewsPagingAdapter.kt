package com.priyesh.newsappmvvm.ui.news.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.LayoutItemNewsAdapterBinding
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.utils.CommonFunctions

class NewsPagingAdapter(private val onNewsClick: (Article) -> Unit) : PagingDataAdapter<Article, NewsPagingAdapter.NewsViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = DataBindingUtil.inflate<LayoutItemNewsAdapterBinding>(LayoutInflater.from(parent.context), R.layout.layout_item_news_adapter, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bindView(it) }
    }

    inner class NewsViewHolder(private val binding: LayoutItemNewsAdapterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindView(article: Article) {
            binding.newsImage.load(article.thumbnail)
            binding.newsTitle.text = article.title
            val dateAndSource = "${CommonFunctions.convertISOToRequiredFormat(article.publishedAt)} | ${article.source}"
            binding.newsDateTimeSource.text = dateAndSource
            binding.author.text = article.author
            itemView.setOnClickListener {
                onNewsClick.invoke(article)
            }
        }
    }

    companion object {
        private val COMPARATOR = object: DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}