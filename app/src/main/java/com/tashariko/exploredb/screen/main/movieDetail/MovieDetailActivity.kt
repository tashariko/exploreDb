package com.tashariko.exploredb.screen.main.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import coil.api.load
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.database.entity.Movie
import com.tashariko.exploredb.databinding.ActivityMovieDetailBinding
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.util.UtilityHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : BaseActivity() {

    private val viewModel by viewModels<MovieDetailViewModel>()
    lateinit var binding: ActivityMovieDetailBinding

    var entityId = 0L

    companion object {

        val DATA_ID = "DATA_ID"

        fun launchActivity(context: Context, id: Long) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(DATA_ID, id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIncomingIntent()
        bindAndSetupUI()
        vmListeners()
        viewlisteners()
    }

    override fun handleIncomingIntent() {
        intent?.extras?.let {
            if (it.containsKey(DATA_ID)) {
                entityId = it.getLong(DATA_ID)
            } else {
                closeWithError(getString(R.string.generic_error_message))
            }
        } ?: run {
            closeWithError(getString(R.string.generic_error_message))
        }
    }

    fun bindAndSetupUI() {
        binding.errorLoadingContainerView.addDataView(binding.topView, javaClass.simpleName)
    }

    fun vmListeners() {
        viewModel.movieDetailLiveData.observe(this, Observer { item ->
            when (item.status) {
                ApiResult.Status.LOADING -> if (item.data == null) {
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
        with(binding) {
            with(data) {
                originalTitle.let {
                    titleView.text = it
                }

                overview.let {
                    overviewView.text = it
                }

                //base url: https://image.tmdb.org/t/p/
                // size : w500/
                // path: 8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
                posterPath.let {
                    imageView.load(
                        "${viewModel.getImagePath(this@MovieDetailActivity).first}${
                            viewModel.getImagePath(
                                this@MovieDetailActivity
                            ).third
                        }${it}"
                    ) {
                        placeholder(R.drawable.icon_movie)
                    }
                }
            }
        }

    }

    fun viewlisteners() {

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