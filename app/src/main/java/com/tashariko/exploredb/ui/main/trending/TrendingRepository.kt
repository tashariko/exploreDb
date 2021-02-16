package com.tashariko.exploredb.ui.main.trending

import com.tashariko.exploredb.application.base.BaseDataSource
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.NetworkBoundRepository
import com.tashariko.exploredb.network.TrendingItemResponse
import com.tashariko.exploredb.network.apiservices.MiscApiService
import com.tashariko.exploredb.network.result.ApiResult
import javax.inject.Inject

class TrendingRepository @Inject constructor(private val userDao: TrendingItemDao,
                                            private val trendingRemoteDataSource: TrendingRemoteDataSource) {

    fun getTrendingItems() = object : NetworkBoundRepository<List<TrendingItem>, TrendingItemResponse>() {

        override fun shouldfetchDataFromDbBeforeNetwork(): Boolean {
            return false
        }

        override fun shouldStoreDataInDbAfterNetwork(): Boolean {
            return false
        }
    }.flowData(
            databaseQuery = {
                null
            },
            networkCall = {
                trendingRemoteDataSource.getProduct()
            },
            saveCallResult = {

            },
            parseNetworkResponse = {
                ApiResult.success(it.results)
            }
    )

}

class TrendingRemoteDataSource @Inject constructor(private val apiService: MiscApiService) : BaseDataSource() {

    suspend fun getProduct()  = getResult {
        apiService.getTrendingItems()
    }
}