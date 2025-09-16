package com.delighted2wins.souqelkhorda.features.sale.presentation

import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Scrap

sealed class SaleIntent {
    data class SendOrder(val order: Order) : SaleIntent()
    object CancelOrder : SaleIntent()
}

data class SaleState(
    val isLoading: Boolean = false,
    val data: List<Scrap> = emptyList(),
    val err: String? = null
)