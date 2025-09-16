package com.delighted2wins.souqelkhorda.features.additem.presentation

import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Scrap

data class AddItemState(
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)

sealed class AddItemIntent {
    data class AddIntent(val scrap: Scrap) : AddItemIntent()
    data class CancelIntent(val scrap: Scrap) : AddItemIntent()
}