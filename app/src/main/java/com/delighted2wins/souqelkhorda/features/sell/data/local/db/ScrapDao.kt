package com.delighted2wins.souqelkhorda.features.sell.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.data.model.ScrapEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScrapDao {
    @Insert()
    suspend fun insertScrap(scrap: ScrapEntity)

    @Query("SELECT * FROM scraps")
    fun getScraps(): List<ScrapEntity>

    @Query("DELETE FROM scraps")
    suspend fun deleteAllScraps()

    @Query("DELETE FROM scraps WHERE id == :id")
    fun deleteScrapById(id: Int): Int

    @Update
    fun updateScrap(scrap: ScrapEntity): Int
}