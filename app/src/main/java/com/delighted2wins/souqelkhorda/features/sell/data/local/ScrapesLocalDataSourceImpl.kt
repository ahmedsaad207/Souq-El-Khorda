package com.delighted2wins.souqelkhorda.features.sell.data.local

import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.data.local.dao.ScrapDao
import com.delighted2wins.souqelkhorda.features.sell.data.mappers.toDomain
import com.delighted2wins.souqelkhorda.features.sell.data.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScrapesLocalDataSourceImpl @Inject constructor(
    private val dao: ScrapDao
) : ScrapesLocalDataSource {
    override suspend fun saveScrap(scrap: Scrap) {
        dao.insertScrap(scrap.toEntity())
    }

    override fun getScraps(): Flow<List<Scrap>> {
        return dao.getScraps().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun deleteAllScraps() = dao.deleteAllScraps()
}