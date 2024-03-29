package com.tashariko.exploredb.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tashariko.exploredb.application.AppConstants.DATABASE_NAME
import com.tashariko.exploredb.database.AppDatabase
import com.tashariko.exploredb.database.dao.MovieDao
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.dao.TrendingRemoteKeysDao
import com.tashariko.exploredb.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
//        val migration_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE user ADD COLUMN email TEXT")
//            }
//        }

        return Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideTrendingItemDao(appDatabase: AppDatabase): TrendingItemDao =
        appDatabase.trendingItemDao()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    @Singleton
    fun provideTrendingRemtoteKeysDao(appDatabase: AppDatabase): TrendingRemoteKeysDao =
        appDatabase.trendingRemtoteKeysDao()
}