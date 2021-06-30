package com.tashariko.exploredb.network.apiservices

import com.tashariko.exploredb.database.entity.Movie
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.network.TrendingItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MiscApiService {

    @GET("3/configuration")
    suspend fun configuration(): Response<ConfigurationResponse>

    @GET("3/trending/all/week")
    suspend fun getTrendingItems(
        @Query("page") page: Int,
        @Query("size") pageSize: Int
    ): Response<TrendingItemResponse>
//
//    @GET("3/tv/{tv_id}")
//    suspend fun getTvDetail(@Query("tv_id") id: Long): Response<TV>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") id: Long): Response<Movie>
}