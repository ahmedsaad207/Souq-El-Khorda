package com.delighted2wins.souqelkhorda.features.history.domain.entity

import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.model.Scrap

data class History(
    val orders: List<HistoryOrder>,
)

data class HistoryOrder(
    val orderId: String = "",
    val userId: String = "",
    val scraps: List<Scrap> = emptyList(),
    val type: OrderType = OrderType.SALE,
    val status: OrderStatus = OrderStatus.PENDING,
    val date: Long = System.currentTimeMillis(),
    val title: String = "",
    val description: String = "",
)