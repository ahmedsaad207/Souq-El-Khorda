package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser


data class OrderDetailsState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val order: Order? = null,
    val user: MarketUser? = null,
)