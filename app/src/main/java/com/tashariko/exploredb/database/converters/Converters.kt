package com.tashariko.exploredb.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.database.entity.USER_LANGUAGE
import com.tashariko.exploredb.database.entity.User

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
    fun fromUser(character: User): String {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        return gson.toJson(character, type)
    }

    @TypeConverter
    fun toUser(string: String): User {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        return gson.fromJson(string, type)
    }

}