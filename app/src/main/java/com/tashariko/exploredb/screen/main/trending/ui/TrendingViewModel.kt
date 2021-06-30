package com.tashariko.exploredb.screen.main.trending.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tashariko.exploredb.application.base.BaseViewModel
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.screen.main.trending.data.TrendingRepository
import javax.inject.Inject

val DEFAULT_PAGE_SIZE = 20

class TrendingViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var repository: TrendingRepository

    var dbSupport = true

    @ExperimentalPagingApi
    fun fetchTrendingList(): LiveData<PagingData<TrendingItem>> {
        return if (dbSupport) {
            repository.getTrendingItemsWithDb().cachedIn(viewModelScope).asLiveData()
        } else {
            repository.getTrendingItemsWithNoDb().cachedIn(viewModelScope).asLiveData()
        }
    }
}