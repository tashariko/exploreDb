package com.tashariko.exploredb.ui.main.trending.data

import androidx.paging.PageKeyedDataSource
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.NetworkBoundRepository
import com.tashariko.exploredb.network.TrendingItemResponse
import com.tashariko.exploredb.network.result.ApiResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Data source for Trending pagination via paging library
 */
class TrendingPageDataSource @Inject constructor(
        private val dataSource: TrendingRemoteDataSource,
        private val dao: TrendingItemDao,
        private val scope: CoroutineScope) : PageKeyedDataSource<Int, TrendingItem>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TrendingItem>) {
        Timber.i("Load--Initial")
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TrendingItem>) {
        Timber.i("Load--After")
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TrendingItem>) {
        Timber.i("Load--Before")
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<TrendingItem>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.getProductList(page, pageSize )
            if (response.status == ApiResult.Status.SUCCESS) {
                val results = response.data!!.results
                dao.insertAll(results)
                callback(results)
            } else if (response.status == ApiResult.Status.ERROR) {
                response.errorType?.message?.let {
                    postError(it)
                }?: run {
                    postError("Error occured")
                }
            }
        }
    }

//    fun getTrendingItems(page: Long, pageSize: Int, callback: (List<TrendingItem>) -> Unit) = object : NetworkBoundRepository<List<TrendingItem>, TrendingItemResponse>() {
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

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        // TODO network error handling
        //networkState.postValue(NetworkState.FAILED)
    }

}