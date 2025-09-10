package com.delighted2wins.souqelkhorda.navigation

data class BottomNavItem(
    val label: String,
    val route: String
)

val bottomNavItem = listOf(
    BottomNavItem("Direct Sale", Routes.Direct_Sale),
    BottomNavItem("Market", Routes.Market),
    BottomNavItem("Nearest Buyers", Routes.Nearest_Buyers)
)