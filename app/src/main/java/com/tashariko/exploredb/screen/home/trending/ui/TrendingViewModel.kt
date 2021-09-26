package com.tashariko.exploredb.screen.home.trending.ui

import androidx.paging.ExperimentalPagingApi
import com.tashariko.exploredb.application.base.BaseViewModel
import com.tashariko.exploredb.screen.home.trending.data.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

val DEFAULT_PAGE_SIZE = 20

@HiltViewModel
class TrendingViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var repository: TrendingRepository

    var dbSupport = true

    @ExperimentalPagingApi
    fun fetchTrendingList() = if (dbSupport) repository.getTrendingItemsWithDb() else repository.getTrendingItemsWithNoDb()
}