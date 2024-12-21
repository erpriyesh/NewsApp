package com.priyesh.newsappmvvm.ui.news.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.priyesh.newsappmvvm.NewsApplication
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.ActivityMainBinding
import com.priyesh.newsappmvvm.ui.news.viewmodel.NewsViewModel
import com.priyesh.newsappmvvm.ui.news.viewmodel.NewsViewModelFactory
import javax.inject.Inject

class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        (application as NewsApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        viewModel.newsUIState.observe(this) {
            when (it) {
                is NewsUIState.Error -> binding.textView.text = it.message
                is NewsUIState.Success -> binding.textView.text = it.result.toString()
                is NewsUIState.Loading -> binding.textView.text = "Loading..."
            }
        }
    }
}