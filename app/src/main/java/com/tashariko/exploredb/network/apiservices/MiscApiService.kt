package com.tashariko.exploredb.network.apiservices

import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.network.TrendingItemResponse
import retrofit2.Response
import retrofit2.http.*

interface MiscApiService {

    @GET("3/configuration")
    suspend fun configuration(): Response<ConfigurationResponse>

    @GET("3/trending/all/week")
    suspend fun getTrendingItems(@Query("page") page: Int): Response<TrendingItemResponse>
}