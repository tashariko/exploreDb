package com.tashariko.exploredb.ui.main.trending.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.ui.main.trending.data.TrendingRemoteDataSource
import com.tashariko.exploredb.ui.main.trending.data.TrendingRepository
import com.tashariko.exploredb.util.SharedPreferenceHelper
import javax.inject.Inject

val DEFAULT_PAGE_SIZE = 20
class TrendingViewModel @Inject constructor(): ViewModel(){

    @Inject
    lateinit var repository: TrendingRepository

    var dbSupport  = true

    @ExperimentalPagingApi
    fun fetchTrendingList(): LiveData<PagingData<TrendingItem>> {
        return if(dbSupport) {
            repository.getTrendingItemsWithDb().cachedIn(viewModelScope).asLiveData()
        }else{
            repository.getTrendingItemsWithNoDb().cachedIn(viewModelScope).asLiveData()
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

}