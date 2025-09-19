package com.delighted2wins.souqelkhorda.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.delighted2wins.souqelkhorda.R

data class NavItem(
    val key: NavKey,
    @StringRes val labelRes: Int,
    val icon: ImageVector
)

val navItems = listOf(
    NavItem(DirectSaleScreen, R.string.sell_screen_title, Icons.Default.Sell),
    NavItem(MarketScreen, R.string.shop_screen_title, Icons.Default.Storefront),
    NavItem(OrdersScreen, R.string.orders_screen_title, Icons.Default.FavoriteBorder),
    NavItem(NearestBuyersScreen, R.string.nearest_screen_title, Icons.Default.LocationOn)
)


