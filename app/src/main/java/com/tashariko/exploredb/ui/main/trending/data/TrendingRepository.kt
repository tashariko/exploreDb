package com.tashariko.exploredb.ui.main.trending.data

import androidx.paging.*
import com.tashariko.exploredb.application.base.BaseDataSource
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.apiservices.MiscApiService
import com.tashariko.exploredb.ui.main.trending.ui.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingRepository @Inject constructor(val remoteDataSource: TrendingRemoteDataSource) {

    fun getTrendingItems(): Flow<PagingData<TrendingItem>> =
        Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { TrendingPageDataSource(remoteDataSource) }
        ).flow


    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }
}


class TrendingRemoteDataSource @Inject constructor(private val apiService: MiscApiService) : BaseDataSource() {

    suspend fun getProductList(page: Int)  = getResult {
        apiService.getTrendingItems(page)
    }
}