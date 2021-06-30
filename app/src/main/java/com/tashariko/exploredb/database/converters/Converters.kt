package com.tashariko.exploredb.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.database.entity.*
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    @TypeConverter
    fun userLangToString(status: USER_LANGUAGE): String? {
        return when (status) {
            USER_LANGUAGE.HINDI -> {
                USER_LANGUAGE.HINDI.language
            }
            USER_LANGUAGE.ENGLISH -> {
                USER_LANGUAGE.ENGLISH.language
            }
        }
    }

    @TypeConverter
    fun stringToUserLang(string: String) = USER_LANGUAGE.valueOf(string)


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
    fun fromMovie(item: Movie): String {
        val gson = Gson()
        val type = object : TypeToken<Movie>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toMovie(string: String): Movie {
        val gson = Gson()
        val type = object : TypeToken<Movie>() {}.type
        return gson.fromJson(string, type)
    }


//
//    @TypeConverter
//    fun fromGenre(item: Genre): String {
//        val gson = Gson()
//        val type = object : TypeToken<Genre>() {}.type
//        return gson.toJson(item, type)
//    }
//
//    @TypeConverter
//    fun toGenre(string: String): Genre {
//        val gson = Gson()
//        val type = object : TypeToken<Genre>() {}.type
//        return gson.fromJson(string, type)
//    }
//
//    @TypeConverter
//    fun fromSpokenLanguage(item: SpokenLanguage): String {
//        val gson = Gson()
//        val type = object : TypeToken<SpokenLanguage>() {}.type
//        return gson.toJson(item, type)
//    }
//
//    @TypeConverter
//    fun toSpokenLanguage(string: String): SpokenLanguage {
//        val gson = Gson()
//        val type = object : TypeToken<SpokenLanguage>() {}.type
//        return gson.fromJson(string, type)
//    }
//
//
//    @TypeConverter
//    fun fromProductionCompany(item: ProductionCompany): String {
//        val gson = Gson()
//        val type = object : TypeToken<ProductionCompany>() {}.type
//        return gson.toJson(item, type)
//    }
//
//    @TypeConverter
//    fun toProductionCompany(string: String): ProductionCompany {
//        val gson = Gson()
//        val type = object : TypeToken<ProductionCompany>() {}.type
//        return gson.fromJson(string, type)
//    }


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

    @TypeConverter
    fun storedGenreToList(data: String): ArrayList<Genre> {
        val gson = Gson()
        if (data == null) {
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<Genre>>() {}.type
        return gson.fromJson<ArrayList<Genre>>(data, listType)
    }

    @TypeConverter
    fun listToStoredGenre(myObjects: ArrayList<Genre>): String {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<Genre>>() {}.type
        return gson.toJson(myObjects, listType)
    }

    @TypeConverter
    fun storedPCToList(data: String): ArrayList<ProductionCompany> {
        val gson = Gson()
        if (data == null) {
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<ProductionCompany>>() {}.type
        return gson.fromJson<ArrayList<ProductionCompany>>(data, listType)
    }

    @TypeConverter
    fun listToStoredProductionCompany(myObjects: ArrayList<ProductionCompany>): String {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<ProductionCompany>>() {}.type
        return gson.toJson(myObjects, listType)
    }

    @TypeConverter
    fun storedSpokenLanguageToList(data: String): ArrayList<SpokenLanguage> {
        val gson = Gson()
        if (data == null) {
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<SpokenLanguage>>() {}.type
        return gson.fromJson<ArrayList<SpokenLanguage>>(data, listType)
    }

    @TypeConverter
    fun listToStoredSpokenLanguage(myObjects: ArrayList<SpokenLanguage>): String {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<SpokenLanguage>>() {}.type
        return gson.toJson(myObjects, listType)
    }

}