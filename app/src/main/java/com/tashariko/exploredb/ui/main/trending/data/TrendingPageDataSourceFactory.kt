package com.tashariko.exploredb.ui.main.trending.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.entity.TrendingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import timber.log.Timber
import javax.inject.Inject

class TrendingPageDataSourceFactory @Inject constructor(
        private val dataSource: TrendingRemoteDataSource,
        private val dao: TrendingItemDao,
        private val scope: CoroutineScope) : DataSource.Factory<Int, TrendingItem>() {

    private val liveData = MutableLiveData<TrendingPageDataSource>()

    override fun create(): DataSource<Int, TrendingItem> {
        val source = TrendingPageDataSource(dataSource, dao, scope)
        scope.launch {
            withContext(Dispatchers.Main){
                liveData.observeForever {
                    Timber.v(it.toString())
                }
            }
        }
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun pagedListConfig() = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()
    }

}