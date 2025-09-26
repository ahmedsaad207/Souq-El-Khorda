package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

data class OffersOrderDetailsState(
    val isLoading: Boolean = false,
    val order: Order? = null,
    val buyerOffer: Pair<Offer, MarketUser>? = null,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)
