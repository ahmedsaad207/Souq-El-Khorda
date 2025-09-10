package com.delighted2wins.souqelkhorda.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.delighted2wins.souqelkhorda.features.sale.presentation.screen.DirectSaleScreen
import com.delighted2wins.souqelkhorda.features.buyers.presentation.screen.NearestBuyersScreen
import com.delighted2wins.souqelkhorda.features.market.presentation.screen.MarketScreen

@Composable
fun SetupNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Direct_Sale,
        modifier = modifier
    ) {
        composable(Routes.Direct_Sale) {
            DirectSaleScreen()
        }

        composable(Routes.Market) {
            MarketScreen()
        }

        composable(Routes.Nearest_Buyers) {
            NearestBuyersScreen()
        }
    }
}