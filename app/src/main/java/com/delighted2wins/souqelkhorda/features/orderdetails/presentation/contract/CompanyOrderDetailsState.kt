package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

sealed class CompanyOrderDetailsState {
    object Empty : CompanyOrderDetailsState()
    object Loading : CompanyOrderDetailsState()
    data class Success(val order: Order, val seller: MarketUser?) : CompanyOrderDetailsState()
    data class Error(val message: String) : CompanyOrderDetailsState()
}