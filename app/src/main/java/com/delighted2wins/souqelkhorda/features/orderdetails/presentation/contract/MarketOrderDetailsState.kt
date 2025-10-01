package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

sealed class MarketOrderDetailsState {
    object Loading : MarketOrderDetailsState()
    data class Error(val message: String) : MarketOrderDetailsState()
    data class Success(
        val order: Order,
        val owner: MarketUser?,
        val isSubmitting: Boolean = false,
    ) : MarketOrderDetailsState()
    object Empty : MarketOrderDetailsState()
}