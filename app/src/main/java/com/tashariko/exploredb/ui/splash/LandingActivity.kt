package com.tashariko.exploredb.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.MainApplication
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.database.entity.User
import com.tashariko.exploredb.databinding.ActivityLandingBinding
import com.tashariko.exploredb.di.util.injectViewModel
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.network.result.ErrorType
import com.tashariko.exploredb.ui.main.MainActivity
import com.tashariko.exploredb.util.SharedPreferenceHelper
import javax.inject.Inject

class LandingActivity: BaseActivity() {

    lateinit var binding: ActivityLandingBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = injectViewModel(viewModelFactory)
        bindVM()
    }

    private fun bindVM() {
        viewModel.createTaskLiveData.observe(this, Observer {
            binding.progressBar.isVisible = false
            binding.retryButton.isVisible = false
            when(it.status) {
                ApiResult.Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                ApiResult.Status.SUCCESS -> {
                    SharedPreferenceHelper.putInSharedPreference(this, AppConstants.SP_KEY_IS_FIRST_TIME, true)
                    startActivity(Intent(this, MainActivity::class.java))
                }
                ApiResult.Status.ERROR -> {
                    it.errorType?.let { et ->
                        if(et.type == ErrorType.Type.Generic) {
                            genericError()
                        }else{
                            et.message?.let {msg ->
                                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                            }?:run{
                                genericError()
                            }
                        }
                    }?:run{
                        genericError()
                    }
                    binding.retryButton.isVisible = true
                }
            }
        })

        viewModel.getConfig(this)
    }

    fun genericError() {
        Snackbar.make(binding.root, getString(R.string.generic_error_message), Snackbar.LENGTH_SHORT).show()
    }

}