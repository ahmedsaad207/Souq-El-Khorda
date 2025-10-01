package com.delighted2wins.souqelkhorda.features.sell.data.datasource

import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.data.local.db.ScrapDao
import com.delighted2wins.souqelkhorda.features.sell.data.mapper.toDomain
import com.delighted2wins.souqelkhorda.features.sell.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScrapLocalDataSourceImpl @Inject constructor(
    private val dao: ScrapDao
) : ScrapLocalDataSource {
    override suspend fun saveScrap(scrap: Scrap) {
        val entity = scrap.toEntity()
        dao.insertScrap(entity)
    }

    override fun getScraps(): Flow<List<Scrap>> = flow {
        val scraps = dao.getScraps().map { scrapEntity ->
            scrapEntity.toDomain()
        }
        emit(scraps)
    }

    override suspend fun deleteAllScraps() = dao.deleteAllScraps()

    override fun deleteScrapById(id: Int): Flow<Int> = flow {
        emit(dao.deleteScrapById(id))
    }

    override fun updateScrap(scrap: Scrap): Flow<Int> = flow {
        val updatedRows = dao.updateScrap(scrap.toEntity())
        emit(updatedRows)
    }
}