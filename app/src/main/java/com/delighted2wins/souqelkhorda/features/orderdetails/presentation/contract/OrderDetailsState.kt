package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Offer

sealed class OrderDetailsState {
    object Loading : OrderDetailsState()
    object Refreshing : OrderDetailsState()
    object Empty : OrderDetailsState()
    data class Error(val message: String) : OrderDetailsState()

    sealed class Success : OrderDetailsState() {
        data class Market(val order: Order) : Success()
        data class Company(val order: Order) : Success()
        data class Sales(val order: Order) : Success()
        data class Offers(val order: Order, val buyerOffer: Offer?) : Success()
    }
}
