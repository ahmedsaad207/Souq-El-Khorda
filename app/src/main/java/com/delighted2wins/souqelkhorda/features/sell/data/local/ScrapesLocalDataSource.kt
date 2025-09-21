package com.delighted2wins.souqelkhorda.features.sell.data.local

import com.delighted2wins.souqelkhorda.core.model.Scrap
import kotlinx.coroutines.flow.Flow

interface ScrapesLocalDataSource {
    suspend fun saveScrap(scrap: Scrap)

    fun getScraps(): Flow<List<Scrap>>

    suspend fun deleteAllScraps()
}