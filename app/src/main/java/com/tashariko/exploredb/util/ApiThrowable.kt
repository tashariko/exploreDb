package com.tashariko.exploredb.util

import timber.log.Timber

class ApiThrowable(val msg: String) : Throwable(msg) {

    init {
        Timber.e("API ERROR: $msg")
    }

}