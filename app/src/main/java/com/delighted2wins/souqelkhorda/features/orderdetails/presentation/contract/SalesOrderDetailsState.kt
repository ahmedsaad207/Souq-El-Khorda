package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

data class SalesOrderDetailsState(
    val order: Order? = null,
    val acceptedOffers: List<Pair<Offer, MarketUser>> = emptyList(),
    val pendingOffers: List<Pair<Offer, MarketUser>> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
