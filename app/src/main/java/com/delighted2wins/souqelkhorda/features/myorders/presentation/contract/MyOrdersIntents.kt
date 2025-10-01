package com.delighted2wins.souqelkhorda.features.myorders.presentation.contract

sealed class MyOrdersIntents{
    object LoadSaleOrders: MyOrdersIntents()
    object LoadOffers: MyOrdersIntents()
    object LoadSells: MyOrdersIntents()
    data class DeleteCompanyOrder(val orderId: String): MyOrdersIntents()
    data class DeleteMarketOrder(val orderId: String): MyOrdersIntents()
}