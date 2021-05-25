package com.tashariko.exploredb.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.tashariko.exploredb.application.AppConstants.SP_FILE_KEY
import timber.log.Timber

object SharedPreferenceHelper {
    private fun getSharedPreferencesObject(context: Context): SharedPreferences {
        return context.getSharedPreferences(SP_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun getLongFromSharedPreferences(context: Context, key: String): Long {
        return getSharedPreferencesObject(context).getLong(key, 0)
    }

    fun getIntFromSharedPreferences(context: Context, key: String): Int {
        return getSharedPreferencesObject(context).getInt(key, 0)
    }

    fun getStringFromSharedPreference(context: Context, key: String): String {
        return getSharedPreferencesObject(context).getString(key, "0")?.let {
            it
        } ?: run {
            "0"
        }
    }

    fun getBooleanFromSharedPreference(context: Context, key: String): Boolean {
        return getSharedPreferencesObject(context).getBoolean(key, false)
    }

    fun getBooleanFromSharedPreference(
        context: Context,
        key: String,
        defaultValue: Boolean
    ): Boolean {
        return getSharedPreferencesObject(context).getBoolean(key, defaultValue)
    }

    @SuppressLint("ApplySharedPref")
    fun <T> putInSharedPreference(context: Context, key: String, t: T) {
        Timber.i("putting in sharedPref $key")
        getSharedPreferencesObject(context).edit(commit = true) {
            when (t) {
                is String -> {
                    putString(key, t as String)
                }
                is Long -> {
                    putLong(key, (t as Long))
                }
                is Int -> {
                    putInt(key, (t as Int))
                }
                is Boolean -> {
                    putBoolean(key, (t as Boolean))
                }
                is Float -> {
                    putFloat(key, (t as Float))
                }
            }
        }
    }

    fun clearSharedPrefForKey(mContext: Context, key: String) {
        getSharedPreferencesObject(mContext).edit {
            remove(key)
        }
    }

    fun clearAll(mContext: Context) {
        getSharedPreferencesObject(mContext).edit(commit = true) {
            clearAll(mContext)
        }
    }
}