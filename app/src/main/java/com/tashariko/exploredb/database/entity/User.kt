package com.tashariko.exploredb.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


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

