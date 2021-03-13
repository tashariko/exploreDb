package com.tashariko.exploredb.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
//
//@Entity(tableName = "tv")
//@Parcelize
//data class TV (
//
//        @PrimaryKey(autoGenerate = true)
//        val localId: Long? = null,
//
//        @SerializedName("backdrop_path")
//        val backdropPath: String,
//
//        @SerializedName("created_by")
//        val createdBy: List<CreatedBy>,
//
//        @SerializedName("episode_run_time")
//        val episodeRunTime: List<Long>,
//
//        @SerializedName("first_air_date")
//        val firstAirDate: String,
//
//        val genres: List<Genre>,
//        val homepage: String,
//        val id: Long,
//
//        @SerializedName("in_production")
//        val inProduction: Boolean,
//
//        val languages: List<String>,
//
//        @SerializedName("last_air_date")
//        val lastAirDate: String,
//
//        @SerializedName("last_episode_to_air")
//        val lastEpisodeToAir: LastEpisodeToAir,
//
//        val name: String,
////
////        @SerializedName("next_episode_to_air")
////        val nextEpisodeToAir: JsonObject? = null,
//
//        val networks: List<Network>,
//
//        @SerializedName("number_of_episodes")
//        val numberOfEpisodes: Long,
//
//        @SerializedName("number_of_seasons")
//        val numberOfSeasons: Long,
//
//        @SerializedName("origin_country")
//        val originCountry: List<String>,
//
//        @SerializedName("original_language")
//        val originalLanguage: String,
//
//        @SerializedName("original_name")
//        val originalName: String,
//
//        val overview: String,
//        val popularity: Double,
//
//        @SerializedName("poster_path")
//        val posterPath: String,
//
//        @SerializedName("production_companies")
//        val productionCompanies: List<Network>,
//
////        @SerializedName("production_countries")
////        val productionCountries: List<ProductionCountry>,
//
//        val seasons: List<Season>,
//
//        @SerializedName("spoken_languages")
//        val spokenLanguages: List<SpokenLanguage>,
//
//        val status: String,
//        val tagline: String,
//        val type: String,
//
//        @SerializedName("vote_average")
//        val voteAverage: Double,
//
//        @SerializedName("vote_count")
//        val voteCount: Long
//) : Parcelable
//
//@Parcelize
//data class CreatedBy (
//        val id: Long,
//
//        @SerializedName("credit_id")
//        val creditID: String,
//
//        val name: String,
//        val gender: Long
////
////        @SerializedName("profile_path")
////        val profilePath: JsonObject? = null
//) : Parcelable
//
//@Parcelize
//data class LastEpisodeToAir (
//        @SerializedName("air_date")
//        val airDate: String,
//
//        @SerializedName("episode_number")
//        val episodeNumber: Long,
//
//        val id: Long,
//        val name: String,
//        val overview: String,
//
//        @SerializedName("production_code")
//        val productionCode: String,
//
//        @SerializedName("season_number")
//        val seasonNumber: Long,
//
//        @SerializedName("still_path")
//        val stillPath: String,
//
//        @SerializedName("vote_average")
//        val voteAverage: Double,
//
//        @SerializedName("vote_count")
//        val voteCount: Long
//) : Parcelable
//
//@Parcelize
//data class Network (
//        val name: String,
//        val id: Long,
//
//        @SerializedName("logo_path")
//        val logoPath: String,
//
//        @SerializedName("origin_country")
//        val originCountry: String
//) : Parcelable
//
//@Parcelize
//data class Season (
//        @SerializedName("air_date")
//        val airDate: String,
//
//        @SerializedName("episode_count")
//        val episodeCount: Long,
//
//        val id: Long,
//        val name: String,
//        val overview: String,
//
//        @SerializedName("poster_path")
//        val posterPath: String,
//
//        @SerializedName("season_number")
//        val seasonNumber: Long
//) : Parcelable
