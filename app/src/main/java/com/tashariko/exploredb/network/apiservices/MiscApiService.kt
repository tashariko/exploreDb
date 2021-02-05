package com.tashariko.exploredb.network.apiservices

import com.google.gson.JsonObject
import com.tashariko.exploredb.network.ConfigurationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MiscApiService {

    @GET("3/configuration")
    suspend fun configuration(): Response<ConfigurationResponse>

}