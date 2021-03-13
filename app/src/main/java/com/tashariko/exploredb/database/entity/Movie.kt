package com.tashariko.exploredb.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class Movie (

        @PrimaryKey(autoGenerate = true)
        val localId: Long? = null,

        val adult: Boolean,

        @SerializedName("backdrop_path")
        val backdropPath: String,

//        @SerializedName("belongs_to_collection")
//        val belongsToCollection: JsonObject? = null,

        @SerializedName("budget")
        val budget: Long,

        @SerializedName("genres")
        val genres: ArrayList<Genre>? = null,

        val homepage: String,
        val id: Long,

        @SerializedName("imdb_id")
        val imdbID: String,

        @SerializedName("original_language")
        val originalLanguage: String,

        @SerializedName("original_title")
        val originalTitle: String,

        val overview: String,
        val popularity: Double,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("production_companies")
        val productionCompanies: ArrayList<ProductionCompany>? = null,

//        @SerializedName("production_countries")
//        val productionCountries: ArrayList<ProductionCountry>? = null,

        @SerializedName("release_date")
        val releaseDate: String,

        val revenue: Long,
        val runtime: Long,

        @SerializedName("spoken_languages")
        val spokenLanguages: ArrayList<SpokenLanguage>? = null,

        val status: String,
        val tagline: String,
        val title: String,
        val video: Boolean,

        @SerializedName("vote_average")
        val voteAverage: Double,

        @SerializedName("vote_count")
        val voteCount: Long
): Parcelable

@Parcelize
data class ProductionCompany (
        val id: Long,

        @SerializedName("logo_path")
        val logoPath: String,

        val name: String,

        @SerializedName("origin_country")
        val originCountry: String
): Parcelable
