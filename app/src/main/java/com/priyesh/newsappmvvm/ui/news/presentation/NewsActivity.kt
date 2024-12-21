package com.priyesh.newsappmvvm.ui.news.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.priyesh.newsappmvvm.NewsApplication
import com.priyesh.newsappmvvm.R
import com.priyesh.newsappmvvm.databinding.ActivityMainBinding
import javax.inject.Inject

class NewsActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        (application as NewsApplication).appComponent.inject(this)
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        binding.lifecycleOwner = this
        binding.vm = newsViewModel
    }
}