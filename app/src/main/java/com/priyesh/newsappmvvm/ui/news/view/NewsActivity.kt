package com.priyesh.newsappmvvm.ui.news.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.ActivityMainBinding
import com.priyesh.newsappmvvm.network.KtorHttpClientBuilder
import com.priyesh.newsappmvvm.network.NetworkConstants
import com.priyesh.newsappmvvm.network.RequestHandler
import com.priyesh.newsappmvvm.ui.news.repository.NewsRepository
import com.priyesh.newsappmvvm.ui.news.viewmodel.NewsViewModel
import com.priyesh.newsappmvvm.ui.news.viewmodel.NewsViewModelFactory
import com.priyesh.newsappmvvm.utils.DispatcherProvider
import io.ktor.http.URLProtocol

class NewsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val httpClient  = KtorHttpClientBuilder().protocol(URLProtocol.HTTPS).host(NetworkConstants.BASE_URL).build()
        val dispathcherProvider = DispatcherProvider()
        val requestHandler = RequestHandler(httpClient, dispathcherProvider)
        val newsRepository = NewsRepository(requestHandler)
        viewModel = ViewModelProvider(this, NewsViewModelFactory(newsRepository))[NewsViewModel::class.java]

        viewModel.newsUIState.observe(this) {
            when(it) {
                is NewsUIState.Error -> binding.textView.text = it.message
                is NewsUIState.Success -> binding.textView.text = it.result.toString()
                is NewsUIState.Loading -> binding.textView.text = "Loading..."
            }
        }
    }
}