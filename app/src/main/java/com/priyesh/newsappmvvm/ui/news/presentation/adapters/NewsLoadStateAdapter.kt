package com.priyesh.newsappmvvm.ui.news.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.priyesh.newsappmvvm.databinding.LayoutItemLoadStateAdapterBinding

class NewsLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<NewsLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LayoutItemLoadStateAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: LayoutItemLoadStateAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.loadStateProgress.isVisible = loadState is LoadState.Loading
            binding.errorView.isVisible = loadState is LoadState.Error
            binding.loadStateRetryButton.setOnClickListener { retry.invoke() }
            /**if (loadState is LoadState.Error) {
                errorMessage.text = loadState.error.localizedMessage
            }*/
        }
    }
}