package com.delighted2wins.souqelkhorda.features.history.data.remote

import com.delighted2wins.souqelkhorda.features.history.data.model.HistoryDto

interface HistoryRemoteDataSource {
    suspend fun getUserOrders(userId: String): Result<HistoryDto>
}