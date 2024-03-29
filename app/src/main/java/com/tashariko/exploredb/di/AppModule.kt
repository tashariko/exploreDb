package com.tashariko.exploredb.di

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import com.tashariko.exploredb.BuildConfig
import com.tashariko.exploredb.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    fun providesBaseUrl(): String =
        if (BuildConfig.DEBUG) "https://api.themoviedb.org/" else "https://api.themoviedb.org/"

    @Provides
    @Singleton
    fun providesRequestInterceptor(application: Application): RequestInterceptor =
        RequestInterceptor(application.applicationContext, BuildConfig.API_DEVELOPER_TOKEN)

    @Provides
    @Singleton
    fun providesOkHttpClient(
        application: Application,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(ChuckInterceptor(application.applicationContext))
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        } else {
            OkHttpClient.Builder()
                .addNetworkInterceptor(requestInterceptor)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient, BASE_URL: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }


}
