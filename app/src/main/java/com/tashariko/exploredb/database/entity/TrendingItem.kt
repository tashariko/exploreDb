package com.tashariko.exploredb.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "trending_item")
@Parcelize
data class TrendingItem (

        @PrimaryKey
        @SerializedName("id")
        val id: Long,

        @SerializedName("backdrop_path")
        val backdropPath: String,

        @SerializedName("genre_ids")
        val genreIDS: ArrayList<Long>,

        @SerializedName("original_language")
        val originalLanguage: String,

        @SerializedName("original_title")
        val originalTitle: String,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("vote_average")
        val voteAverage: Double,

        @SerializedName("vote_count")
        val voteCount: Long,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("release_date")
        val releaseDate: String,

        @SerializedName("video")
        val video: Boolean,

        @SerializedName("adult")
        val adult: Boolean,

        @SerializedName("popularity")
        val popularity: Double,

        @SerializedName("media_type")
        val mediaType: String
): Parcelable
