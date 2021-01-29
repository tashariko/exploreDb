package com.tashariko.exploredb.application.base

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.tashariko.exploredb.network.result.ErrorType
import com.tashariko.exploredb.network.result.Result
import retrofit2.Response
import timber.log.Timber


/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Result.success(body)
        }
        Timber.e("${response.code()}")
        var msg = ""
        response.errorBody()?.let {
            val jsonObject: JsonObject = JsonParser().parse(it.string()).asJsonObject
            msg = jsonObject.get("err").asString
        }
        return if(msg.isNullOrEmpty()){
            Result.error( errorType = ErrorType(ErrorType.Type.Backend, "Error: ${response.errorBody()} ${response.message()}"))
        }else{
            Result.error( errorType = ErrorType(ErrorType.Type.Backend, "Error: $msg"))
        }
    }

}

