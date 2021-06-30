package com.tashariko.exploredb.screen.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.util.SharedPreferenceHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var repository: LandingRepository

    private val _tempConfigLiveData = MutableLiveData<ApiResult<ConfigurationResponse>>()
    val createTaskLiveData: LiveData<ApiResult<ConfigurationResponse>>
        get() = _tempConfigLiveData

    fun getConfig(context: Context) {
        viewModelScope.launch {
            try {
                repository.getData().collect {
                    if (it.status == ApiResult.Status.SUCCESS) {
                        val gson = Gson()
                        val type = object : TypeToken<ConfigurationResponse>() {}.type
                        val configString = gson.toJson(it.data!!, type)
                        SharedPreferenceHelper.putInSharedPreference(
                            context,
                            AppConstants.SP_KEY_CONFIG,
                            configString
                        )
                    }
                    _tempConfigLiveData.postValue(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

    }

}