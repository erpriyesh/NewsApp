package com.priyesh.newsappmvvm.ui.news.presentation.news_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.FragmentNewsBinding
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.ui.news.domain.model.Category
import com.priyesh.newsappmvvm.ui.news.presentation.NewsViewModel
import com.priyesh.newsappmvvm.utils.CommonFunctions
import com.priyesh.newsappmvvm.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()

    private lateinit var categoryAdapter: NewsCategoryAdapter
    private lateinit var latestNewsAdapter: LatestNewsAdapter
    private lateinit var newsListAdapter: NewsListAdapter

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
        binding.toolbar.subtitle =
            CommonFunctions.timeMillisToRequiredFormat(System.currentTimeMillis())
        categoryAdapter.submitList(viewModel.getCategoryList())
    }

    private fun initObservers() {
        viewModel.news.observe(viewLifecycleOwner) {
            latestNewsAdapter.submitList(listOf(it[0]))
            newsListAdapter.submitList(it.subList(1, it.size))
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
        newsListAdapter = NewsListAdapter(::onNewsClick)

        val recyclerViewCategory = RecyclerView(requireActivity()).apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
            setPadding(0, 0, 48, 0)
            clipToPadding = false
        }

        val recyclerViewLatestNews = RecyclerView(requireActivity()).apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = latestNewsAdapter
        }

        val concatAdapter = ConcatAdapter(
            CommonFunctions.createSingleViewAdapter(recyclerViewCategory),
            CommonFunctions.createSingleViewAdapter(recyclerViewLatestNews),
            newsListAdapter
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
            })
    }
}