package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class MarketOrderDetailsEffect{
    data class ShowSuccess(val message: String) : MarketOrderDetailsEffect()
    data class ShowError(val message: String) : MarketOrderDetailsEffect()
}