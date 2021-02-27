package com.tashariko.exploredb.ui.main.trending

import android.content.Context
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.util.SharedPreferenceHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class TrendingViewModel @Inject constructor(): ViewModel(){

    @Inject
    lateinit var repository: TrendingRepository

    private val _tempTrendingLiveData = MutableLiveData<ApiResult<List<TrendingItem>>>()
    val trendingLiveData: LiveData<ApiResult<List<TrendingItem>>>
        get() = _tempTrendingLiveData


    fun getTrendingItems() {

        viewModelScope.launch {
            repository.getTrendingItems().collect {
                _tempTrendingLiveData.postValue(it)
            }
        }
    }

    fun getImagePath(mContext: Context): Triple<String, String, String> {
        val string = SharedPreferenceHelper.getStringFromSharedPreference(
            mContext,
            AppConstants.SP_KEY_CONFIG
        )
        val type = object : TypeToken<ConfigurationResponse>() {}.type
        val config: ConfigurationResponse = Gson().fromJson(string, type)

        with(config.images) {
            var original = ""
            var downed = ""
            for (item in this.poster_sizes) {
                if(item == "original"){
                    original = item
                }else if(item == "w154") {
                    downed = item
                }
            }

            return Triple(base_url,downed, original)
        }

    }


    //POINTER: Will start calling api as soon as we start observing trendingItem
//    var trendingItem: LiveData<ApiResult<List<TrendingItem>>> = liveData {
//        emitSource(repository.getTrendingItems().asLiveData())
//    }

}