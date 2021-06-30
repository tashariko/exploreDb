package com.tashariko.exploredb.application.base

import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.network.result.ErrorType
import retrofit2.Response


/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResult<T> {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return ApiResult.success(body)
        }

        var msg = ""
        /**
         * If want to handle some message from backend
         */
//        response.errorBody()?.let {
//            val jsonObject: JsonObject = JsonParser().parse(it.string()).asJsonObject
//            msg = jsonObject.get("err").asString
//        }
        return if (msg.isNullOrEmpty()) {
            ApiResult.error(
                errorType = ErrorType(
                    ErrorType.Type.Backend,
                    "Error: ${response.errorBody()} ${response.message()}"
                )
            )
        } else {
            ApiResult.error(errorType = ErrorType(ErrorType.Type.Backend, "Error: $msg"))
        }
    }

}

