package com.delighted2wins.souqelkhorda.features.sell.domain.repo

import com.delighted2wins.souqelkhorda.core.model.Scrap
import kotlinx.coroutines.flow.Flow

interface ScrapRepository {
    suspend fun saveScrap(scrap: Scrap)

    fun getScraps(): Flow<List<Scrap>>

    suspend fun deleteAllScraps()

    fun deleteScrapById(id: Int): Flow<Int>

    fun updateScrap(scrap: Scrap): Flow<Int>
}