package com.tashariko.exploredb.network.result

class ErrorType(val type: Type, val message: String? = null) {

    enum class Type {
        Backend,
        Generic
    }


}