package com.delighted2wins.souqelkhorda.navigation

import androidx.navigation3.runtime.NavKey
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import kotlinx.serialization.Serializable

@Serializable
data object DirectSaleScreen:NavKey
@Serializable
data object NearestBuyersScreen:NavKey
@Serializable
data object MarketScreen:NavKey
@Serializable
data object OrdersScreen:NavKey
@Serializable
data object SplashScreen:NavKey

@Serializable
data object LoginScreen:NavKey

@Serializable
data object SignUpScreen:NavKey

@Serializable
data class AddItemKey(val category: String):NavKey

@Serializable
data object ProfileScreen: NavKey
@Serializable
data object NotificationsScreen: NavKey
@Serializable
data object HistoryScreen: NavKey
@Serializable
data class OrderDetailsKey(
    val orderId: String,
    val orderOwnerId: String,
    val orderBuyerId: String? = null,
    val source: OrderSource
) : NavKey

@Serializable
data object BuyerRegistration: NavKey

