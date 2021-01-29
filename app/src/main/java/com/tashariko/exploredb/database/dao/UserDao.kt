package com.tashariko.exploredb.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tashariko.exploredb.database.entity.User


@Dao
interface UserDao {

    @Query("SELECT * FROM `User` WHERE id = :userId")
    fun getUser(userId: Long): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}
