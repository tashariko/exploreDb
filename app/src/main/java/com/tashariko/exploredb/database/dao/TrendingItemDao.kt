package com.tashariko.exploredb.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tashariko.exploredb.database.entity.TrendingItem


@Dao
interface TrendingItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(legoSets: List<TrendingItem>)


    @Query("SELECT * FROM trending_item")
    fun getPagedLists(): DataSource.Factory<Int, TrendingItem>


}
