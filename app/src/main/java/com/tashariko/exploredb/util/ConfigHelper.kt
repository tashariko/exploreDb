package com.tashariko.exploredb.util

import com.tashariko.exploredb.database.entity.DbConfig
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants

object ConfigHelper {

    fun getConfigJson(mContext: Context): JsonObject? {
        return try {
            val gson = Gson()
            val type = object : TypeToken<JsonObject>() {}.type
            gson.fromJson(
                SharedPreferenceHelper.getStringFromSharedPreference(
                    mContext,
                    AppConstants.SP_KEY_CONFIG
                ), type
            )
        } catch (ex: Exception) {
            null
        }
    }

    fun isConfigAvailable(mContext: Context): Boolean {
        getConfigJson(mContext)?.let {
            return true
        } ?: run {
            return false
        }
    }
}