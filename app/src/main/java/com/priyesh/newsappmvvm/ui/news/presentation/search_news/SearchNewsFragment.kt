package com.priyesh.newsappmvvm.ui.news.presentation.search_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.FragmentSearchNewsBinding
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.ui.news.presentation.NewsViewModel
import com.priyesh.newsappmvvm.ui.news.presentation.adapters.NewsLoadStateAdapter
import com.priyesh.newsappmvvm.ui.news.presentation.adapters.NewsPagingAdapter
import com.priyesh.newsappmvvm.utils.Constants
import com.priyesh.newsappmvvm.utils.onQueryTextSubmit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsListAdapter: NewsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_news, container, false)
        viewModel.getSearchedNews("india")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchView.onQueryTextSubmit {
            viewModel.getSearchedNews(if (it.isNullOrEmpty()) "" else it)
        }

        viewModel.searchedNews.observe(viewLifecycleOwner) {
            newsListAdapter.submitData(lifecycle, it)
        }
    }

    private fun initRecyclerView() {
        newsListAdapter = NewsPagingAdapter(::onNewsClick)
        binding.newsRv.adapter = newsListAdapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter { newsListAdapter.retry() },
            footer = NewsLoadStateAdapter { newsListAdapter.retry() }
        )
        newsListAdapter.addLoadStateListener { loadStates ->
            binding.loadOrError.loading.isVisible = loadStates.refresh is LoadState.Loading
            binding.loadOrError.errorViewLl.isVisible = loadStates.refresh is LoadState.Error

            val isListEmpty = loadStates.refresh is LoadState.NotLoading && newsListAdapter.itemCount == 0
            binding.loadOrError.emptyView.isVisible = isListEmpty
            binding.newsRv.isVisible = !isListEmpty
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNewsClick(article: Article) {
        findNavController().navigate(
            R.id.action_searchNewsFragment_to_newsDetailsFragment,
            Bundle().apply {
                putString(Constants.ARTICLE_URL, article.url)
            })
    }
}