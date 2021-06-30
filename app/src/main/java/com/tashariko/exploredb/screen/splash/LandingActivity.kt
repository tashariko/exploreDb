package com.tashariko.exploredb.screen.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.databinding.ActivityLandingBinding
import com.tashariko.exploredb.di.util.injectViewModel
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.network.result.ErrorType
import com.tashariko.exploredb.screen.main.MainActivity
import javax.inject.Inject

class LandingActivity : BaseActivity() {

    companion object {

        fun launchLandingScreen(context: Context) {
            val mainIntent = Intent(context, LandingActivity::class.java)
            context.startActivity(mainIntent)
            (context as BaseActivity).finish()
        }
    }

    lateinit var binding: ActivityLandingBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = injectViewModel(viewModelFactory)
        bindAndSetupUI()
        vmListeners()
    }

    override fun handleIncomingIntent() {

    }

    override fun bindAndSetupUI() {
        binding.retryButton.setOnClickListener {
            viewModel.getConfig(this)
        }
    }

    override fun vmListeners() {
        viewModel.createTaskLiveData.observe(this, Observer {
            binding.progressBar.isVisible = false
            binding.retryButton.isVisible = false
            when (it.status) {
                ApiResult.Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                ApiResult.Status.SUCCESS -> {
                    MainActivity.launchScreen(this)
                }
                ApiResult.Status.ERROR -> {
                    it.errorType?.let { et ->
                        if (et.type == ErrorType.Type.Generic) {
                            showToast(getString(R.string.generic_error_message))
                        } else {
                            et.message?.let { msg ->
                                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                            } ?: run {
                                showToast(getString(R.string.generic_error_message))
                            }
                        }
                    } ?: run {
                        showToast(getString(R.string.generic_error_message))
                    }
                    binding.retryButton.isVisible = true
                }
            }
        })

        viewModel.getConfig(this)
    }

    override fun viewlisteners() {

    }

}