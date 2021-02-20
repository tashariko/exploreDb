package com.tashariko.exploredb.ui.main.trending

import android.util.Log
import androidx.lifecycle.*
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.result.ApiResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import java.lang.Runnable
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


    //POINTER: Will start calling api as soon as we start observing trendingItem
//    var trendingItem: LiveData<ApiResult<List<TrendingItem>>> = liveData {
//        emitSource(repository.getTrendingItems().asLiveData())
//    }

}