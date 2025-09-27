package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class CompanyOrderDetailsIntent {
    data class LoadOrder(val orderId: String, val ownerId: String) : CompanyOrderDetailsIntent()
    object Refresh : CompanyOrderDetailsIntent()
}