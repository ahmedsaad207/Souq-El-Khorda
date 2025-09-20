package com.delighted2wins.souqelkhorda.features.myorders.presentation.contract

sealed class MyOrdersIntents{
    object LoadSaleOrders: MyOrdersIntents()
    object LoadOffers: MyOrdersIntents()
    object LoadSells: MyOrdersIntents()
}