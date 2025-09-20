package com.delighted2wins.souqelkhorda.features.myorders.presentation.contract

data class MyOrdersState(
    val isLoading: Boolean = false,
    val saleOrders: List<String> = emptyList(),
    val offers: List<String> = emptyList(),
    val sells: List<String> = emptyList(),
    val error: String? = null
) {
    val saleCount: Int get() = saleOrders.size
    val offersCount: Int get() = offers.size
    val sellsCount: Int get() = sells.size
}
