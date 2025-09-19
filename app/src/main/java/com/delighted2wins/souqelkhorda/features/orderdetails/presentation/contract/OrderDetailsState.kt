package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import com.delighted2wins.souqelkhorda.features.market.domain.entities.User

data class OrderDetailsState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val order: ScrapOrder? = null,
    val user: User? = null,
)