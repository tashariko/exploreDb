package com.tashariko.exploredb.network.result

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

data class ApiResult<out T>(
    val status: Status,
    val data: T? = null,
    val errorType: ErrorType? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ApiResult<T> {
            return ApiResult(Status.SUCCESS, data, null)
        }

        fun <T> error(errorType: ErrorType? = null, data: T? = null): ApiResult<T> {
            return ApiResult(Status.ERROR, data, errorType)
        }

        fun <T> loading(data: T? = null): ApiResult<T> {
            return ApiResult(Status.LOADING, data, null)
        }
    }
}