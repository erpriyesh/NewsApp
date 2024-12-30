package com.priyesh.newsappmvvm.ui.news.presentation.news_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.LayoutItemTopNewsBinding
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.utils.CommonFunctions

class LatestNewsAdapter(private val onNewsClick: (Article) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var articleList: List<Article>? = null

    fun submitList(list: List<Article>) {
        articleList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutItemTopNewsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_top_news,
            parent,
            false
        )
        return LatestNewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        articleList?.get(position)?.let { (holder as LatestNewsViewHolder).bindView(it) }
    }

    inner class LatestNewsViewHolder(private val binding: LayoutItemTopNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(article: Article) {
            binding.topNewsImage.load(article.urlToImage)
            binding.title.text = article.title
            val dateAndSource =
                "${CommonFunctions.convertISOToRequiredFormat(article.publishedAt)} | ${article.source?.name}"
            binding.dateTimeSource.text = dateAndSource
            itemView.setOnClickListener {
                onNewsClick.invoke(article)
            }
        }
    }
}