package com.priyesh.newsappmvvm.ui.news.presentation.news_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.FragmentNewsBinding
import com.priyesh.newsappmvvm.ui.news.data.model.Category
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.ui.news.presentation.NewsViewModel
import com.priyesh.newsappmvvm.ui.news.presentation.adapters.NewsLoadStateAdapter
import com.priyesh.newsappmvvm.ui.news.presentation.adapters.NewsPagingAdapter
import com.priyesh.newsappmvvm.utils.CommonFunctions
import com.priyesh.newsappmvvm.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()

    private lateinit var categoryAdapter: NewsCategoryAdapter
    private lateinit var latestNewsAdapter: LatestNewsAdapter
    private lateinit var newsListAdapter: NewsPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        initObservers()
    }

    private fun initView() {
        initRecyclerView()
        binding.toolbar.subtitle = CommonFunctions.timeMillisToRequiredFormat(System.currentTimeMillis())
        categoryAdapter.submitList(viewModel.getCategoryList())
        binding.loadOrError.btnRetry.setOnClickListener { newsListAdapter.retry() }
    }

    private fun initObservers() {
        viewModel.news.observe(viewLifecycleOwner) {
            if (it != null) {
                newsListAdapter.submitData(lifecycle, it)
                lifecycleScope.launch {
                    newsListAdapter.loadStateFlow.collectLatest { loadStates ->
                        if (loadStates.refresh is LoadState.NotLoading &&
                            newsListAdapter.snapshot().items.isNotEmpty()
                        ) {
                            latestNewsAdapter.submitList(listOf(newsListAdapter.snapshot().items[0]))
                        }
                    }
                }
            }
        }

        newsListAdapter.addLoadStateListener { loadStates ->
            binding.loadOrError.loading.isVisible = loadStates.refresh is LoadState.Loading
            binding.loadOrError.errorViewLl.isVisible = loadStates.refresh is LoadState.Error

            val isListEmpty = loadStates.refresh is LoadState.NotLoading && newsListAdapter.itemCount == 0
            binding.loadOrError.emptyView.isVisible = isListEmpty
            binding.homeRecyclerView.isVisible = !isListEmpty
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.icSearch.setOnClickListener {
            findNavController().navigate(R.id.action_newsFragment_to_searchNewsFragment)
        }
        binding.icSetting.setOnClickListener { /*Not in Use*/ }
    }

    private fun initRecyclerView() {
        categoryAdapter = NewsCategoryAdapter(::onCategorySelected)
        latestNewsAdapter = LatestNewsAdapter(::onNewsClick)
        newsListAdapter = NewsPagingAdapter(::onNewsClick)
        binding.categoryRv.adapter = categoryAdapter

        val recyclerViewLatestNews = RecyclerView(requireActivity()).apply {
            layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = latestNewsAdapter
        }
        val concatAdapter = ConcatAdapter(
            CommonFunctions.createSingleViewAdapter(recyclerViewLatestNews),
            newsListAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { newsListAdapter.retry() },
                footer = NewsLoadStateAdapter { newsListAdapter.retry() }
            )
        )
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.homeRecyclerView.adapter = concatAdapter
    }

    private fun onCategorySelected(category: Category) {
        viewModel.loadNews(category.category)
    }

    private fun onNewsClick(article: Article) {
        findNavController().navigate(
            R.id.action_newsFragment_to_newsDetailsFragment,
            Bundle().apply {
                putString(Constants.ARTICLE_URL, article.url)
            }
        )
    }
}