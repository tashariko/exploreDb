package com.tashariko.exploredb.screen.main.trending.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseViewModel
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.screen.main.trending.data.TrendingRepository
import com.tashariko.exploredb.util.SharedPreferenceHelper
import javax.inject.Inject

val DEFAULT_PAGE_SIZE = 20
class TrendingViewModel @Inject constructor(): BaseViewModel(){

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

}