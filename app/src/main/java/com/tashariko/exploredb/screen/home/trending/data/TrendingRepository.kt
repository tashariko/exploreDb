package com.tashariko.exploredb.screen.home.trending.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tashariko.exploredb.application.base.BaseDataSource
import com.tashariko.exploredb.database.AppDatabase
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.dao.TrendingRemoteKeysDao
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.network.apiservices.MiscApiService
import com.tashariko.exploredb.screen.home.trending.ui.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingRepository @Inject constructor(
    private val remoteDataSource: TrendingRemoteDataSource,
    val trendingItemDao: TrendingItemDao,
    val trendingRemoteKeysDao: TrendingRemoteKeysDao,
    val appDatabase: AppDatabase
) {

    fun getTrendingItemsWithNoDb(): Flow<PagingData<TrendingItem>> =
        Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { TrendingPageDataSource(remoteDataSource) }
        ).flow

    @ExperimentalPagingApi
    fun getTrendingItemsWithDb(): Flow<PagingData<TrendingItem>> {

        val pagingSourceFactory = {
            trendingItemDao.getAllItems()
        }
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = TrendingMediator(
                remoteDataSource,
                trendingItemDao,
                trendingRemoteKeysDao,
                appDatabase
            )
        ).flow
    }


    private fun getDefaultPageConfig(): PagingConfig {
        //dont change initialLoadSize, its making it to go for infinite calls
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = 20
        )
    }

}


class TrendingRemoteDataSource @Inject constructor(private val apiService: MiscApiService) :
    BaseDataSource() {

    suspend fun getProductList(page: Int, pageSize: Int) = getResult {
        apiService.getTrendingItems(page, pageSize)
    }
}