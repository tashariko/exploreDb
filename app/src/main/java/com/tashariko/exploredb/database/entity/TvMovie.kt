package com.tashariko.exploredb.database.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Genre(
    val id: Long,
    val name: String
) : Parcelable

@Parcelize
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso3166_1: String,

    val name: String
) : Parcelable

@Parcelize
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("iso_639_1")
    val iso639_1: String,

    val name: String
) : Parcelable
