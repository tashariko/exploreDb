package com.tashariko.exploredb.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tashariko.exploredb.database.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged


@Dao
interface UserDao {

    //Just returns the user
    @Query("SELECT * FROM `User` WHERE id = :userId")
    fun getUser(userId: Long): User

    //Returns livedata of the user
    @Query("SELECT * FROM `User` WHERE id = :userId")
    fun getLiveUser(userId: Long): LiveData<User>

    //POINTER: Returns flow object and will return whenever there is change in data in db
    @Query("SELECT * FROM `User` WHERE id = :userId")
    fun getFlowUser(userId: Long): Flow<User>


    //POINTER: In this case, it will only emit until changed for user "userId"
    fun getUserDistinctUntilChanged(userId: Long) =
            getFlowUser(userId).distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}
