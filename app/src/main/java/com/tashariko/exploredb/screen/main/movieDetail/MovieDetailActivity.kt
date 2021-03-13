package com.tashariko.exploredb.screen.main.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.database.entity.Movie
import com.tashariko.exploredb.databinding.ActivityItemDetailBinding
import com.tashariko.exploredb.databinding.ActivityMovieDetailBinding
import com.tashariko.exploredb.databinding.FragmentTrendingBinding
import com.tashariko.exploredb.di.util.injectViewModel
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.util.UtilityHelper
import javax.inject.Inject

class MovieDetailActivity: BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MovieDetailViewModel

    lateinit var binding: ActivityMovieDetailBinding
    var entityId = 0L
    companion object{

        val DATA_ID = "DATA_ID"

        fun launchActivity(context: Context, id: Long) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(DATA_ID,id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = injectViewModel(viewModelFactory)
        handleIncomingIntent()
        bindAndSetupUI()
        vmListeners()
        viewlisteners()
    }

    override fun handleIncomingIntent() {
        intent?.extras?.let {
            if(it.containsKey(DATA_ID)){
                entityId = it.getLong(DATA_ID)
            }else{
                closeWithError(getString(R.string.generic_error_message))
            }
        }?:run{
            closeWithError(getString(R.string.generic_error_message))
        }
    }

    override fun bindAndSetupUI() {
        binding.errorLoadingContainerView.addDataView(binding.topView, javaClass.simpleName)
    }

    override fun vmListeners() {
        viewModel.movieDetailLiveData.observe(this, Observer { item ->
            when (item.status) {
                ApiResult.Status.LOADING -> if (item.data == null ) {
                    configureView(AppConstants.LOADING_LAYOUT, AppConstants.VIEW_FROM_LOADING)
                } else {
                    configureView(AppConstants.DATA_LAYOUT, AppConstants.VIEW_FROM_LOADING)
                    updateUI(item.data)
                }
                ApiResult.Status.SUCCESS -> {
                    updateUI(item.data!!)

                    configureView(AppConstants.DATA_LAYOUT, AppConstants.VIEW_FROM_API)
                }
                ApiResult.Status.ERROR -> if (!UtilityHelper.showDataInError()) {
                    configureView(AppConstants.ERROR_LAYOUT, AppConstants.VIEW_FROM_ERROR)
                } else {
                    if (item.data == null) {
                        configureView(AppConstants.ERROR_LAYOUT, AppConstants.VIEW_FROM_ERROR)
                    } else {
                        configureView(AppConstants.DATA_LAYOUT, AppConstants.VIEW_FROM_ERROR)
                        updateUI(item.data)
                    }
                }
            }
        })

        viewModel.getMovieDetail(entityId)
    }

    private fun updateUI(data: Movie) {
        binding.textView.text = data.title
    }

    override fun viewlisteners() {

    }

    private fun configureView(errorLayout: String, attribute: String) {
        when (errorLayout) {
            AppConstants.NO_DATA_LAYOUT -> {
                binding.errorLoadingContainerView.showDefaultNoDataView(attribute)
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