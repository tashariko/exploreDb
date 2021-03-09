package com.tashariko.exploredb.ui.main.trending.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseFragment
import com.tashariko.exploredb.databinding.FragmentTrendingBinding
import com.tashariko.exploredb.di.util.injectViewModel
import com.tashariko.exploredb.ui.main.trending.ui.adapter.LoadingErrorAdapter
import com.tashariko.exploredb.ui.main.trending.ui.adapter.TrendingAdapter
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

        adapter.setBaseImageUrl(viewModel.getImagePath(requireContext()))

        binding.errorLoadingContainerView.addDataView(binding.swipeRefreshLayout, javaClass.simpleName)
        binding.recyclerView.adapter = adapter.withLoadStateFooter(LoadingErrorAdapter{
            adapter.retry()
        })
    }

    override fun vmListeners() {
        viewModel.fetchTrendingList().observe(viewLifecycleOwner, Observer { listResource ->
            listResource?.let {
                adapter.submitData(lifecycle, it)
            }
        })

    }

    override fun viewlisteners() {

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading){
                configureView(AppConstants.LOADING_LAYOUT, AppConstants.VIEW_FROM_LOADING)
            }
            else{
                configureView(AppConstants.DATA_LAYOUT, AppConstants.VIEW_FROM_API)

                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(requireActivity(), it.error.message, Toast.LENGTH_LONG).show()
                }

            }


        }

        binding.swipeRefreshLayout.setOnRefreshListener { adapter.retry() }
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