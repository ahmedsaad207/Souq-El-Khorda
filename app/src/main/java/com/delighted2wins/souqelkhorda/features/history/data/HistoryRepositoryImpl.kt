package com.delighted2wins.souqelkhorda.features.history.data

import com.delighted2wins.souqelkhorda.features.authentication.data.local.IAuthenticationLocalDataSource
import com.delighted2wins.souqelkhorda.features.history.data.mapper.toDomain
import com.delighted2wins.souqelkhorda.features.history.data.remote.HistoryRemoteDataSource
import com.delighted2wins.souqelkhorda.features.history.domain.entity.History
import com.delighted2wins.souqelkhorda.features.history.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyRemoteDataSource: HistoryRemoteDataSource,
    private val authLocalDataSource: IAuthenticationLocalDataSource,
) : HistoryRepository {
    override suspend fun getUserOrders(): Result<History> {
        val userId = authLocalDataSource.getCashedUser().id
        return historyRemoteDataSource.getUserOrders(userId).map { it.toDomain() }
    }
}