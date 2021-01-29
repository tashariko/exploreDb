package com.tashariko.exploredb.database.entity

import com.google.gson.annotations.SerializedName


enum class USER_STATUS(val status: String) {
    @SerializedName("loggedIn")
    LOGGED_IN("loggedIn"),

    @SerializedName("loggedOut")
    LOGGED_OUT("loggedOut")
}

enum class USER_LANGUAGE(val language: String) {
    @SerializedName("hi")
    HINDI("hi"),

    @SerializedName("en")
    ENGLISH("en")
}