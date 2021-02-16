package com.tashariko.exploredb.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.database.entity.USER_LANGUAGE
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    @TypeConverter fun userLangToString(status: USER_LANGUAGE): String? {
        return when (status) {
            USER_LANGUAGE.HINDI -> {
                USER_LANGUAGE.HINDI.language
            }
            USER_LANGUAGE.ENGLISH -> {
                USER_LANGUAGE.ENGLISH.language
            }
        }
    }

    @TypeConverter fun stringToUserLang(string: String) = USER_LANGUAGE.valueOf(string)


    @TypeConverter
    fun fromTrendingItem(item: TrendingItem): String {
        val gson = Gson()
        val type = object : TypeToken<TrendingItem>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toTrendingItem(string: String): TrendingItem {
        val gson = Gson()
        val type = object : TypeToken<TrendingItem>() {}.type
        return gson.fromJson(string, type)
    }


    @TypeConverter
    fun storedLongToList(data: String): ArrayList<Long> {
        val gson = Gson()
        if (data == null) {
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<Long>>() {}.type
        return gson.fromJson<ArrayList<Long>>(data, listType)
    }

    @TypeConverter
    fun listToStoredLong(myObjects: ArrayList<Long>): String {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<Long>>() {}.type
        return gson.toJson(myObjects, listType)
    }

}