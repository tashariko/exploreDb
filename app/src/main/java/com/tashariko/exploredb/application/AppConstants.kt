package com.tashariko.exploredb.application

object AppConstants {
    const val DATABASE_NAME = "exploreDb.db"
    const val SP_FILE_KEY = "exploreDbPreferences"
    const val SP_KEY_CONFIG = "spKeyConfig"
    const val SP_KEY_IS_FIRST_TIME = "spIsFirstTime"

    //Error Loading View
    const val NO_DATA_LAYOUT = "No-Data-Layout"
    const val DATA_LAYOUT = "Data-Layout"
    const val ERROR_LAYOUT = "Error-Layout"
    const val LOADING_LAYOUT = "Loading-Layout"

    const val VIEW_FROM_LOADING = "viewFromLoading"
    const val VIEW_FROM_API = "viewFromApi"
    const val VIEW_FROM_ERROR = "viewFromError"

    enum class MediaType(val value: String) {
        TV("tv"), MOVIE("movie")
    }

}