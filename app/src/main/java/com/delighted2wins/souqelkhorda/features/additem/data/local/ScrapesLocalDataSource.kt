package com.delighted2wins.souqelkhorda.features.additem.data.local

import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Scrap
import kotlinx.coroutines.flow.Flow

interface ScrapesLocalDataSource {
    suspend fun saveScrap(scrap: Scrap)

    fun getScraps(): Flow<List<Scrap>>

    suspend fun deleteAllScraps()
}