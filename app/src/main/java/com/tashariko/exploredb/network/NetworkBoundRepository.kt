package com.tashariko.exploredb.network

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

        // emit(someValue) is similar to myData.value = someValue whereas
        // emitSource(someLiveValue) is similar to myData = someLiveValue.
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