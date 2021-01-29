package com.tashariko.exploredb.di.module


import com.tashariko.exploredb.network.apiservices.MiscApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class ApiServiceModule {

    @Provides
    @Singleton
    fun provideMiscApiService(retrofit: Retrofit): MiscApiService {
        return retrofit.create(MiscApiService::class.java)
    }
}