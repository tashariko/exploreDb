package com.tashariko.exploredb.ui.main.trending.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tashariko.exploredb.application.base.BaseDataSource
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.apiservices.MiscApiService
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TrendingRepository @Inject constructor(private val dao: TrendingItemDao,
                                            private val trendingRemoteDataSource: TrendingRemoteDataSource
) {

    fun observePagedSets(connectivityAvailable: Boolean, coroutineScope: CoroutineScope) =
        if (connectivityAvailable) observeRemotePagedSets(coroutineScope)
        else observeLocalPagedSets()

    private fun observeLocalPagedSets(): LiveData<PagedList<TrendingItem>> {
        val dataSourceFactory = dao.getPagedLists()

        return LivePagedListBuilder(dataSourceFactory, TrendingPageDataSourceFactory.pagedListConfig()).build()
    }

    private fun observeRemotePagedSets(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<TrendingItem>> {
        val dataSourceFactory = TrendingPageDataSourceFactory(trendingRemoteDataSource,
            dao, ioCoroutineScope)
        return LivePagedListBuilder(dataSourceFactory,
            TrendingPageDataSourceFactory.pagedListConfig()).build()
    }

//    fun getTrendingItems(page: Long) = object : NetworkBoundRepository<List<TrendingItem>, TrendingItemResponse>() {
//
//        override fun shouldfetchDataFromDbBeforeNetwork(): Boolean {
//            return false
//        }
//
//        override fun shouldStoreDataInDbAfterNetwork(): Boolean {
//            return false
//        }
//    }.flowData(
//            databaseQuery = {
//                null
//            },
//            networkCall = {
//                trendingRemoteDataSource.getProductList(page, pageSize)
//            },
//            saveCallResult = {
//
//            },
//            parseNetworkResponse = {
//                ApiResult.success(it.results)
//            }
//    )
}


class TrendingRemoteDataSource @Inject constructor(private val apiService: MiscApiService) : BaseDataSource() {

    suspend fun getProductList(page: Int, pageSize: Int = 0)  = getResult {
        apiService.getTrendingItems(page)
    }
}