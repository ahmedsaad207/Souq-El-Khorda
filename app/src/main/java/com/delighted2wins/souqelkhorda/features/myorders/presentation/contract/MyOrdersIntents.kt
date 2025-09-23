package com.delighted2wins.souqelkhorda.features.myorders.presentation.contract

sealed class MyOrdersIntents{
    object LoadSaleOrders: MyOrdersIntents()
    object LoadOffers: MyOrdersIntents()
    object LoadSells: MyOrdersIntents()

    data class DeleteCompanyOrder(val orderId: String): MyOrdersIntents()
    data class DeclineOffer(val offerId: String): MyOrdersIntents()
    data class DeclineSell(val orderId: String): MyOrdersIntents()
}