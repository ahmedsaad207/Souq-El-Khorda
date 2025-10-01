package com.delighted2wins.souqelkhorda.features.history.data.mapper

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.history.data.model.HistoryDto
import com.delighted2wins.souqelkhorda.features.history.domain.entity.History
import com.delighted2wins.souqelkhorda.features.history.domain.entity.HistoryOrder

fun HistoryDto.toDomain(): History {
    return History(
        orders = orders.map { it.toDomain() }
    )
}

fun Order.toDomain(): HistoryOrder {
    return HistoryOrder(
        orderId = orderId,
        userId = userId,
        scraps = scraps,
        type = type,
        status = status,
        date = date,
        title = title,
        description = description,
    )
}