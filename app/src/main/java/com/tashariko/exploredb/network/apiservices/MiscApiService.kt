package com.tashariko.exploredb.network.apiservices

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MiscApiService {

    @GET("/configuration")
    suspend fun getCongiguration(@Query("teamId") teamId: Long): Response<JsonObject>

}