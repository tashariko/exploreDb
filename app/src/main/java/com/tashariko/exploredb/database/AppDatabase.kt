package com.tashariko.exploredb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tashariko.exploredb.database.converters.Converters
import com.tashariko.exploredb.database.dao.MovieDao
import com.tashariko.exploredb.database.dao.TrendingItemDao
import com.tashariko.exploredb.database.dao.TrendingRemoteKeysDao
import com.tashariko.exploredb.database.dao.UserDao
import com.tashariko.exploredb.database.entity.Movie
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.database.entity.TrendingRemtoteKey
import com.tashariko.exploredb.database.entity.User


@Database(
    entities = [User::class, TrendingItem::class, TrendingRemtoteKey::class, Movie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
    abstract fun trendingItemDao(): TrendingItemDao
    abstract fun trendingRemtoteKeysDao(): TrendingRemoteKeysDao
}