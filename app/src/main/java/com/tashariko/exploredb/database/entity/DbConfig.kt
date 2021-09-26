package com.tashariko.exploredb.database.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DbConfig (
    @SerializedName("images")
    val images: Images,

    @SerializedName("change_keys")
    val changeKeys: List<String>
): Parcelable

@Parcelize
data class Images (
    @SerializedName("base_url")
    val baseURL: String,

    @SerializedName("secure_base_url")
    val secureBaseURL: String,

    @SerializedName("backdrop_sizes")
    val backdropSizes: List<String>,

    @SerializedName("logo_sizes")
    val logoSizes: List<String>,

    @SerializedName("poster_sizes")
    val posterSizes: List<String>,

    @SerializedName("profile_sizes")
    val profileSizes: List<String>,

    @SerializedName("still_sizes")
    val stillSizes: List<String>
): Parcelable
