package com.tashariko.exploredb.ui.main.trending.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.di.CoroutineScropeIO
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.ui.main.trending.data.TrendingPageDataSource
import com.tashariko.exploredb.ui.main.trending.data.TrendingRemoteDataSource
import com.tashariko.exploredb.ui.main.trending.data.TrendingRepository
import com.tashariko.exploredb.util.SharedPreferenceHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

val DEFAULT_PAGE_SIZE = 20
class TrendingViewModel @Inject constructor(private val dataSource: TrendingRemoteDataSource): ViewModel(){

    @Inject
    lateinit var repository: TrendingRepository

    fun fetchTrendingList(): LiveData<PagingData<TrendingItem>> {
        return repository.getTrendingItems().cachedIn(viewModelScope).asLiveData()
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

}