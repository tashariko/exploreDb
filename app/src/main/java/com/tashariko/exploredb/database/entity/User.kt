package com.tashariko.exploredb.database.entity

import android.os.Parcelable
import android.util.JsonWriter
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.io.IOException
import java.lang.reflect.Type


@Entity(tableName = "user")
@Parcelize
data class User(
        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        val id: Long,

        @ColumnInfo(name = "name")
        @SerializedName("name")
        val name: String,

        @ColumnInfo(name = "imgUrl")
        @SerializedName("imgUrl")
        val imageUrl: String? = null,

        @ColumnInfo(name = "language")
        @SerializedName("language")
        val language: USER_LANGUAGE,

        @ColumnInfo(name = "registeredAt")
        @SerializedName("registeredAt")
        val registeredAt: String
) : Parcelable {

    override fun toString() = name
}

