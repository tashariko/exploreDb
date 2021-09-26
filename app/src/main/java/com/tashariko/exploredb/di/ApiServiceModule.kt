package com.tashariko.exploredb.di


import com.tashariko.exploredb.network.apiservices.MiscApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Provides
    @Singleton
    fun provideMiscApiService(retrofit: Retrofit): MiscApiService {
        return retrofit.create(MiscApiService::class.java)
    }
}