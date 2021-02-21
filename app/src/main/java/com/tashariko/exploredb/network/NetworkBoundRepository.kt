package com.tashariko.exploredb.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tashariko.exploredb.network.result.ErrorType
import com.tashariko.exploredb.network.result.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception

/**
 * A repository which provides resource from local database as well as remote end point.
 *
 * [RESULT] represents the type for database.
 * [REQUEST] represents the type for network.
 */
@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun flowData(databaseQuery: () -> Flow<RESULT>?,
                 networkCall: suspend () -> ApiResult<REQUEST>,
                 saveCallResult: suspend (REQUEST) -> Unit,
                 parseNetworkResponse: (REQUEST) -> ApiResult<RESULT>
    ): Flow<ApiResult<RESULT>> = flow<ApiResult<RESULT>> {

        emit(ApiResult.loading())

        if(shouldfetchDataFromDbBeforeNetwork()) {
            databaseQuery.invoke()?.let {flow ->
                val source = flow.map { ApiResult.success(it) }
                emit(ApiResult.loading(source.first().data))
            }?: run {
                emit(ApiResult.error<RESULT>(ErrorType(ErrorType.Type.Generic), null))
                throw Exception("Provide datebaseQuery as shouldfetchDataFromDbBeforeNetwork is true")
            }
        }

        //run it(remoteSource.fetchData()) here on running invoke method
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == ApiResult.Status.SUCCESS) {
            responseStatus.data?.let { req ->
                if(shouldStoreDataInDbAfterNetwork()) {
                    saveCallResult(req)
                }
                emit(parseNetworkResponse(req))
            }?: run {
                emit(ApiResult.error<RESULT>(responseStatus.errorType,null))
                throw Exception("Response is null")
            }

        } else if (responseStatus.status == ApiResult.Status.ERROR) {
            if(shouldfetchDataFromDbBeforeNetwork()) {
                databaseQuery.invoke()?.let { flow ->
                    val source = flow.map { ApiResult.success(it) }
                    emit(ApiResult.error<RESULT>(responseStatus.errorType,source.first().data))
                }?: run {
                    emit(ApiResult.error<RESULT>(ErrorType(ErrorType.Type.Generic), null))
                    throw Exception("Provide datebaseQuery as shouldfetchDataFromDbBeforeNetwork is true")
                }
            }else{
                emit(ApiResult.error<RESULT>(responseStatus.errorType,null))
            }
        }


    }.flowOn(Dispatchers.IO).catch { e ->
        emit(ApiResult.error<RESULT>(ErrorType(ErrorType.Type.Generic), null))
        e.printStackTrace()
    }


    /**
     * return false when we dont want to save database transactions for this response.
     */
    protected open fun shouldfetchDataFromDbBeforeNetwork(): Boolean {
        return true
    }


    /**
     * return false when we dont want to save database transactions for this response.
     */
    protected open fun shouldStoreDataInDbAfterNetwork(): Boolean {
        return true
    }
}

//
//
//private const val UNSPLASH_STARTING_PAGE_INDEX = 1
//
//class NBRPagingSource<K : Any,V: Any, REQUEST, RESULT>(
//        val networkCall: suspend () -> ApiResult<REQUEST>,
//        parseNetworkResponse: (REQUEST) -> ApiResult<RESULT>
//): PagingSource<K, V>() {
//
//
//    override fun getRefreshKey(state: PagingState<K, V>): K? {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun load(params: PagingSource.LoadParams<K>): PagingSource.LoadResult<K, V> {
//        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
//
//        return try {
//            val response = networkCall.invoke()
//            val data = response.data
//
//            LoadResult.Page(
//                    data = data,
//                    prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
//                    nextKey = if (photos.isEmpty()) null else position + 1
//            )
//        } catch (exception: IOException) {
//            LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            LoadResult.Error(exception)
//        }
//    }
//
//
//}
