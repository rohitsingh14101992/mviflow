package com.example.mviflow.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mviflow.NewsApp
import com.example.mviflow.R
import com.example.mviflow.databinding.NewsListBinding
import com.example.mviflow.list.di.DaggerNewsListComponent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
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
        newsListViewModel.sendEvent(NewsListIntent.LoadNews)
        subscribeToViewEffectsWithLiveData()
    }

    private fun subscribeToViewEffectsWithLiveData() {
        newsListViewModel.observeViewEffects().asLiveData()
            .observe(this, Observer<NewsListViewEffects> {
                Toast.makeText(this, "News Success", Toast.LENGTH_LONG).show()
            })

    }

    private fun initDI() {
        DaggerNewsListComponent.builder().appComponent((this.application as NewsApp).appComponent)
            .build().inject(this)
    }


    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.news_list)
        binding.setLifecycleOwner(this)
        newsListViewModel =
            ViewModelProvider(this, newsListViewModelFactory)[NewsListViewModel::class.java]
        binding.viewModel = newsListViewModel
    }
}
