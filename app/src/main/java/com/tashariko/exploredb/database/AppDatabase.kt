package com.tashariko.exploredb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tashariko.exploredb.database.converters.Converters
import com.tashariko.exploredb.database.dao.UserDao
import com.tashariko.exploredb.database.entity.User


@Database(
        entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}