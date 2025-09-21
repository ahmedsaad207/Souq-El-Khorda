package com.delighted2wins.souqelkhorda.features.sell.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.delighted2wins.souqelkhorda.features.sell.data.local.entities.ScrapDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface ScrapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScrap(scrap: ScrapDatabase)

    @Query("SELECT * FROM scraps")
    fun getScraps(): Flow<List<ScrapDatabase>>

    @Query("DELETE FROM scraps")
    suspend fun deleteAllScraps()
}