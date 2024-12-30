package com.priyesh.newsappmvvm.ui.news.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.FragmentSearchNewsBinding
import com.priyesh.newsappmvvm.ui.news.domain.model.Article
import com.priyesh.newsappmvvm.ui.news.presentation.news_home.NewsListAdapter
import com.priyesh.newsappmvvm.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_news, container, false)
        viewModel.searchNews("india", 1)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListAdapter = NewsListAdapter(::onNewsClick)
        binding.newsRv.adapter = newsListAdapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNews(query ?: "india", 1)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.searchedNews.observe(viewLifecycleOwner) {
            newsListAdapter.submitList(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
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