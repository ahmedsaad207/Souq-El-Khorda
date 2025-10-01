package com.delighted2wins.souqelkhorda.features.sell.data.repo

import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.data.datasource.ScrapLocalDataSource
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.ScrapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScrapRepositoryImpl @Inject constructor(private val local: ScrapLocalDataSource) :
    ScrapRepository {
    override suspend fun saveScrap(scrap: Scrap) {
        local.saveScrap(scrap)
    }

    override fun getScraps(): Flow<List<Scrap>> {
        return local.getScraps()
    }

    override suspend fun deleteAllScraps() = local.deleteAllScraps()

    override fun deleteScrapById(id: Int): Flow<Int> {
        return local.deleteScrapById(id)
    }

    override fun updateScrap(scrap: Scrap): Flow<Int> {
        return local.updateScrap(scrap)
    }
}