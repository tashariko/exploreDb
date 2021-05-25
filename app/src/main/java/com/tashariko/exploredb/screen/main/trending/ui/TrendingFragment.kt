package com.tashariko.exploredb.screen.main.trending.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseFragment
import com.tashariko.exploredb.databinding.FragmentTrendingBinding
import com.tashariko.exploredb.screen.main.SetMainTitle
import com.tashariko.exploredb.screen.main.trending.ui.adapter.LoadingErrorAdapter
import com.tashariko.exploredb.screen.main.trending.ui.adapter.TrendingAdapter
import com.tashariko.exploredb.util.NetworkObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingFragment : BaseFragment() {

    private val viewModel by viewModels<TrendingViewModel>()

    lateinit var binding: FragmentTrendingBinding
    lateinit var delegate: SetMainTitle

    private val adapter: TrendingAdapter by lazy {
        TrendingAdapter()
    }

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        rootview = binding.root

        handleIncomingIntent()
        bindAndSetupUI()
        vmListeners()
        viewlisteners()

        return rootview
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            delegate = context as SetMainTitle
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement SetMainTitle")
        }
    }

    override fun handleIncomingIntent() {

    }

    override fun bindAndSetupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        adapter.setBaseImageUrl(viewModel.getImagePath(requireContext()))

        binding.errorLoadingContainerView.addDataView(
            binding.swipeRefreshLayout,
            javaClass.simpleName
        )
        binding.recyclerView.adapter = adapter.withLoadStateFooter(LoadingErrorAdapter {
            adapter.retry()
        })
        //adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    @ExperimentalPagingApi
    override fun vmListeners() {
        viewModel.fetchTrendingList().observe(viewLifecycleOwner, Observer { listResource ->
            listResource?.let {
                adapter.submitData(lifecycle, it)
            }
        })

        NetworkObserver.getNetLiveData(requireActivity()).observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.offlineContainerView.isVisible = it
            } ?: run {
                binding.offlineContainerView.isVisible = false
            }
        })
    }

    override fun viewlisteners() {
        delegate.setTitle(getString(R.string.fragment_trending_title))

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                configureView(AppConstants.LOADING_LAYOUT, AppConstants.VIEW_FROM_LOADING)
            } else {
                binding.swipeRefreshLayout.isRefreshing = false
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