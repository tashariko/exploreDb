package com.tashariko.exploredb.screen.splash

import com.tashariko.exploredb.application.base.BaseDataSource
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.network.NetworkBoundRepository
import com.tashariko.exploredb.network.apiservices.MiscApiService
import com.tashariko.exploredb.network.result.ApiResult
import javax.inject.Inject


class LandingRepository @Inject constructor(
    private val landingRemoteDataSource: LandingRemoteDataSource
) {

    fun getData() =
        object : NetworkBoundRepository<ConfigurationResponse, ConfigurationResponse>() {
            override fun shouldfetchDataFromDbBeforeNetwork(): Boolean {
                return false
            }

            override fun shouldStoreDataInDbAfterNetwork(): Boolean {
                return false
            }
        }.flowData(
            databaseQuery = {
                null
            },
            networkCall = {
                landingRemoteDataSource.getData()
            },
            saveCallResult = {

            },
            parseNetworkResponse = {
                ApiResult.success(it)
            }
        )

}

class LandingRemoteDataSource @Inject constructor(private val service: MiscApiService) :
    BaseDataSource() {

    suspend fun getData() = getResult {
        service.configuration()
    }
}