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
data class MarketOrderDetailsKey(
    val orderId: String,
    val orderOwnerId: String
) : NavKey

@Serializable
data class CompanyOrderDetailsKey(
    val orderId: String,
    val orderOwnerId: String
) : NavKey

@Serializable
data class SalesOrderDetailsKey(
    val orderId: String,
) : NavKey

@Serializable
data class OffersOrderDetailsKey(
    val orderId: String,
) : NavKey

@Serializable
data class ChatKey(
    val orderId: String,
    val sellerId: String,
    val buyerId: String,
    val offerId: String
) : NavKey

data object BuyerRegistration: NavKey

