package com.delighted2wins.souqelkhorda.features.sell.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap

sealed class SellIntent {
    data class SendOrder(val order: Order) : SellIntent()

    data class AddScrap(val scrap: Scrap) : SellIntent()

    data class EditScrap(val scrap: Scrap) : SellIntent()

    data class DeleteScrap(val scrap: Scrap) : SellIntent()

    object Cancel : SellIntent()
}

data class SellState(
    val isLoading: Boolean = false,
    val data: List<Scrap> = emptyList(),
    val err: String? = null,
    val isScrapSaved: Boolean = false,
    val isScrapDeleted: Boolean = false
)