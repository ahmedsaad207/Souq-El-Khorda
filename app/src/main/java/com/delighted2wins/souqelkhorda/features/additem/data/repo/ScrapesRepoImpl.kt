package com.delighted2wins.souqelkhorda.features.additem.data.repo

import com.delighted2wins.souqelkhorda.features.sell.data.local.ScrapesLocalDataSource
import com.delighted2wins.souqelkhorda.features.additem.domain.repo.ScrapesRepo
import com.delighted2wins.souqelkhorda.core.model.Scrap
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScrapesRepoImpl @Inject constructor(private val local: ScrapesLocalDataSource) : ScrapesRepo {
    override suspend fun saveScrap(scrap: Scrap) {
        local.saveScrap(scrap)
    }

    override fun getScraps(): Flow<List<Scrap>> {
        return local.getScraps()
    }

    override suspend fun deleteAllScraps() = local.deleteAllScraps()
}