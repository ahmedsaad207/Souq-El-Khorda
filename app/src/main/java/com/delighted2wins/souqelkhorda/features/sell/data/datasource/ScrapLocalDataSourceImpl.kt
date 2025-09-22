package com.delighted2wins.souqelkhorda.features.sell.data.datasource

import android.util.Log
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.data.local.db.ScrapDao
import com.delighted2wins.souqelkhorda.features.sell.data.mapper.toDomain
import com.delighted2wins.souqelkhorda.features.sell.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScrapLocalDataSourceImpl @Inject constructor(
    private val dao: ScrapDao
) : ScrapLocalDataSource {
    override suspend fun saveScrap(scrap: Scrap) {
        val entity = scrap.toEntity()
        Log.d("TAG", "local/saveScrap: id=${entity.id}")
        dao.insertScrap(entity)
    }

    override fun getScraps(): Flow<List<Scrap>> {
        return dao.getScraps().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun deleteAllScraps() = dao.deleteAllScraps()

    override fun deleteScrapById(id: Int): Flow<Int> = flow {
        emit(dao.deleteScrapById(id))
    }
}