package com.tashariko.exploredb.ui.main.trending

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.application.base.BaseFragment
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.databinding.ActivityMainBinding
import com.tashariko.exploredb.databinding.FragmentTrendingBinding
import com.tashariko.exploredb.di.util.injectViewModel
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.util.UtilityHelper
import kotlinx.android.synthetic.main.fragment_trending.*
import java.util.ArrayList
import javax.inject.Inject

class TrendingFragment @Inject constructor(): BaseFragment()  {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentTrendingBinding
    lateinit var viewModel: TrendingViewModel

    private val adapter: TrendingAdapter by lazy {
        TrendingAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        rootview = binding.root
        viewModel = injectViewModel(viewModelFactory)
        handleIncomingIntent()
        bindAndSetupUI()
        vmListeners()
        viewlisteners()

        return rootview
    }


    override fun handleIncomingIntent() {

    }

    override fun bindAndSetupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.errorLoadingContainerView.addDataView(binding.swipeRefreshLayout, javaClass.simpleName)
        binding.recyclerView.adapter = adapter
    }

    override fun vmListeners() {
        viewModel.trendingLiveData.observe(viewLifecycleOwner, Observer { listResource ->
            binding.swipeRefreshLayout.isRefreshing = false
            when (listResource.status) {
                ApiResult.Status.LOADING -> if (listResource.data == null || listResource.data.isEmpty()) {
                    configureView(AppConstants.LOADING_LAYOUT, AppConstants.VIEW_FROM_LOADING)
                } else {
                    configureView(AppConstants.DATA_LAYOUT, AppConstants.VIEW_FROM_LOADING)
                    adapter.submitList(listResource.data as ArrayList<TrendingItem>)
                }
                ApiResult.Status.SUCCESS -> if (listResource.data!!.isNotEmpty()) {
                    configureView(AppConstants.DATA_LAYOUT, AppConstants.VIEW_FROM_API)
                    adapter.submitList(listResource.data as ArrayList<TrendingItem>)
                } else if (adapter.itemCount == 0) {
                    configureView(AppConstants.NO_DATA_LAYOUT, AppConstants.VIEW_FROM_API)
                }
                ApiResult.Status.ERROR -> if (!UtilityHelper.showDataInError()) {
                    configureView(AppConstants.ERROR_LAYOUT, AppConstants.VIEW_FROM_ERROR)
                } else {
                    if (listResource.data == null || listResource.data != null && listResource.data.isEmpty()) {
                        configureView(AppConstants.ERROR_LAYOUT, AppConstants.VIEW_FROM_ERROR)
                    } else {
                        configureView(AppConstants.DATA_LAYOUT, AppConstants.VIEW_FROM_ERROR)
                        adapter.submitList(listResource.data as ArrayList<TrendingItem>)
                    }
                }
            }
        })

        viewModel.getTrendingItems()
    }

    override fun viewlisteners() {
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.getTrendingItems() }
    }

    private fun configureView(errorLayout: String, attribute: String) {
        binding.emptyView.visibility = View.GONE
        when (errorLayout) {
            AppConstants.NO_DATA_LAYOUT -> {
                binding.emptyView.visibility = View.VISIBLE
                binding.errorLoadingContainerView.hideView()
            }
            AppConstants.ERROR_LAYOUT -> {
                binding.errorLoadingContainerView.showDefaultErrorView(attribute)
            }
            AppConstants.LOADING_LAYOUT -> {
                binding.errorLoadingContainerView.showDefaultLoadingView(attribute)
            }
            AppConstants.DATA_LAYOUT -> {
                binding.errorLoadingContainerView.showDataView(attribute)
            }
        }
    }

}