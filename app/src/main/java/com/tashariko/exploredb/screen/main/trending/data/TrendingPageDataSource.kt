package com.tashariko.exploredb.screen.main.trending.data

import androidx.paging.*
import androidx.room.withTransaction
import com.tashariko.exploredb.database.AppDatabase
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.dao.TrendingRemoteKeysDao
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.database.entity.TrendingRemtoteKey
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.network.result.ErrorType
import com.tashariko.exploredb.util.ApiThrowable
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.io.InvalidObjectException

/**
 * Data source for Trending pagination via paging library without database
 */

val DEFAULT_PAGE_INDEX = 1


/**
 * Handling without pagination
 */
class TrendingPageDataSource constructor(val trendingRemoteDataSource: TrendingRemoteDataSource) :
    PagingSource<Int, TrendingItem>() {

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, TrendingItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingItem> {
        Timber.i("Load Started")

        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: DEFAULT_PAGE_INDEX
            val loadSize = params.loadSize
            val response = trendingRemoteDataSource.getProductList(nextPage, loadSize)

            if (response.status == ApiResult.Status.SUCCESS) {
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage + 1
                )
            } else {
                if (response.errorType!!.type == ErrorType.Type.Generic) {
                    LoadResult.Error(ApiThrowable("Error in api: GENERIC"))
                } else {
                    LoadResult.Error(ApiThrowable("Error in api: BACKEND"))
                }
            }

        } catch (e: Exception) {
            LoadResult.Error(e)

        }
    }
}


/**
 * If want pagination  with database support
 */
@ExperimentalPagingApi
class TrendingMediator constructor(
    val trendingRemoteDataSource: TrendingRemoteDataSource,
    val trendingItemDao: TrendingItemDao,
    val remoteKeysDao: TrendingRemoteKeysDao,
    val appDatabase: AppDatabase
) :
    RemoteMediator<Int, TrendingItem>() {


    /**
     * If want to refresh the database
     */
//    override suspend fun initialize(): InitializeAction {
//        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
//        return if (System.currentTimeMillis() - db.lastUpdated() >= cacheTimeout)
//        {
//            // Cached data is up-to-date, so there is no need to re-fetch
//            // from the network.
//            InitializeAction.SKIP_INITIAL_REFRESH
//        } else {
//            // Need to refresh cached data from network; returning
//            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
//            // APPEND and PREPEND from running until REFRESH succeeds.
//            InitializeAction.LAUNCH_INITIAL_REFRESH
//        }
//
//    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TrendingItem>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = trendingRemoteDataSource.getProductList(page, state.config.pageSize)

            response.data!!.results.let { list ->

                val isEndOfList = list.isEmpty()

                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        remoteKeysDao.clearRemoteKeys()
                        trendingItemDao.clearAllItems()
                    }

                    val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                    val nextKey = if (isEndOfList) null else page + 1
                    val keys = list.map { item ->
                        TrendingRemtoteKey(repoId = item.id, prevKey = prevKey, nextKey = nextKey)
                    }

                    remoteKeysDao.insertAll(keys)
                    trendingItemDao.insertAll(list)
                }

                return MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, TrendingItem>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                val key = remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
                Timber.i("Refresh: $key")
                key
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                //crashing here
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                val key = remoteKeys.nextKey
                Timber.i("Append: $key")
                key
            }
            LoadType.PREPEND -> {
                /**
                 * To handle when we are handling prepend
                 */
                /*val remoteKeys = getFirstRemoteKey(state)
                ?: throw InvalidObjectException("Invalid state, key should not be null")
                val key = remoteKeys.prevKey
                Timber.i("Prepend: $key")
                key*/
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, TrendingItem>): TrendingRemtoteKey? {
        val pages = state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { tItem -> remoteKeysDao.remoteKeysTrendingId(tItem.id) }

        Timber.i("TAG__${pages?.localId}")

        return pages
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, TrendingItem>): TrendingRemtoteKey? {
        //crashing when pages is giving 0 count insteead of 20
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { tItem -> remoteKeysDao.remoteKeysTrendingId(tItem.id) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, TrendingItem>): TrendingRemtoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeysDao.remoteKeysTrendingId(repoId)
            }
        }
    }


}