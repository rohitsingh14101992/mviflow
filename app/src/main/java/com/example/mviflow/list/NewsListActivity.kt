package com.example.mviflow.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mviflow.NewsApp
import com.example.mviflow.R
import com.example.mviflow.databinding.NewsListBinding
import com.example.mviflow.list.di.DaggerNewsListComponent
import javax.inject.Inject

class NewsListActivity : AppCompatActivity() {

    @Inject
    lateinit var newsListViewModelFactory: NewsListViewModelFactory
    lateinit var newsListViewModel: NewsListViewModel
    lateinit var binding: NewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDI()
        initDataBinding()
        binding.rvNewsList.layoutManager = LinearLayoutManager(this)
        binding.rvNewsList.adapter = NewsListAdapter()
        newsListViewModel.sendEvent(NewsListAction.LoadNews)
    }

    private fun initDI() {
        DaggerNewsListComponent.builder().appComponent((this.application as NewsApp).appComponent)
            .build().inject(this)
    }


    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.news_list)
        binding.setLifecycleOwner(this)
        newsListViewModel =
            ViewModelProviders.of(this, newsListViewModelFactory)[NewsListViewModel::class.java]
        binding.viewModel = newsListViewModel
    }
}
