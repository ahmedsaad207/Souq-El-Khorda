package com.delighted2wins.souqelkhorda.features.history.domain.repository

import com.delighted2wins.souqelkhorda.features.history.domain.entity.History

interface HistoryRepository {
    suspend fun getUserOrders(): Result<History>
}