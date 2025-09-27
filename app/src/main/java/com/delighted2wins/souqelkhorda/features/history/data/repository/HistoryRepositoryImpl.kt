package com.delighted2wins.souqelkhorda.features.history.data.repository

import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.model.Order
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

    override suspend fun addOrder(order: Order): Boolean {
        return historyRemoteDataSource.addOrder(order)
    }

    override suspend fun addOrderOffer(order: Order, buyerId: String): Boolean {
        return historyRemoteDataSource.addOrderOffer(order, buyerId)
    }

    override suspend fun updateOrderStatus(orderId: String, userId: String,orderType: String, status: OrderStatus): Boolean {
        return historyRemoteDataSource.updateOrderStatus(orderId, userId, orderType,status)
    }

}