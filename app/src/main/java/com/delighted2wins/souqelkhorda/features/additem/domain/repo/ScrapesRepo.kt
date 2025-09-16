package com.delighted2wins.souqelkhorda.features.additem.domain.repo

import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Scrap
import kotlinx.coroutines.flow.Flow

interface ScrapesRepo {
    suspend fun saveScrap(scrap: Scrap)

    fun getScraps(): Flow<List<Scrap>>

    suspend fun deleteAllScraps()
}