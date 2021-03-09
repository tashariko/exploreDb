package com.tashariko.exploredb.ui.main.trending.data

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.NetworkBoundRepository
import com.tashariko.exploredb.network.TrendingItemResponse
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.network.result.ErrorType
import com.tashariko.exploredb.util.ApiThrowable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Data source for Trending pagination via paging library
 */

val DEFAULT_PAGE_INDEX = 1

class TrendingPageDataSource constructor(val trendingRemoteDataSource: TrendingRemoteDataSource): PagingSource<Int, TrendingItem>() {


    override fun getRefreshKey(state: PagingState<Int, TrendingItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingItem> {
        Timber.i("Load Started")

        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: DEFAULT_PAGE_INDEX
            val loadSize = params.loadSize
            val response = trendingRemoteDataSource.getProductList(nextPage)

            if(response.status == ApiResult.Status.SUCCESS){
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage + 1
                )
            }else{
                if(response.errorType!!.type == ErrorType.Type.Generic) {
                    LoadResult.Error(ApiThrowable("Error in api: GENERIC"))
                }else{
                    LoadResult.Error(ApiThrowable("Error in api: BACKEND"))
                }
            }

        } catch (e: Exception) {
            LoadResult.Error(e)

        }
    }

}