package com.delighted2wins.souqelkhorda.features.additem.data.local

import com.delighted2wins.souqelkhorda.core.local.db.ScrapDao
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Scrap
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScrapesLocalDataSourceImpl @Inject constructor(
    private val dao: ScrapDao
) : ScrapesLocalDataSource {
    override suspend fun saveScrap(scrap: Scrap) {
        dao.insertScrap(scrap)
    }

    override fun getScraps(): Flow<List<Scrap>> {
        return dao.getScraps()
    }

    override suspend fun deleteAllScraps() = dao.deleteAllScraps()
}