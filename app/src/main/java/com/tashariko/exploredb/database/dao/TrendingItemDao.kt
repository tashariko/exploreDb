package com.tashariko.exploredb.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.database.entity.TrendingRemtoteKey


@Dao
interface TrendingItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(doggoModel: List<TrendingItem>)

    @Query("SELECT * FROM trending_item")
    fun getAllItems(): PagingSource<Int, TrendingItem>

    @Query("DELETE FROM trending_item")
    suspend fun clearAllItems()

}

@Dao
interface TrendingRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<TrendingRemtoteKey>)

    @Query("SELECT * FROM trending_remote_key WHERE repoId = :id")
    suspend fun remoteKeysTrendingId(id: Long): TrendingRemtoteKey?

    @Query("DELETE FROM trending_remote_key")
    suspend fun clearRemoteKeys()
}