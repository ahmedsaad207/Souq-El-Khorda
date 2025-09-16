package com.delighted2wins.souqelkhorda.core.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Scrap
import kotlinx.coroutines.flow.Flow

@Dao
interface ScrapDao {
    @Insert
    suspend fun insertScrap(scrap: Scrap)

    @Query("SELECT * FROM scraps")
    fun getScraps(): Flow<List<Scrap>>

    @Query("DELETE FROM scraps")
    suspend fun deleteAllScraps()
}