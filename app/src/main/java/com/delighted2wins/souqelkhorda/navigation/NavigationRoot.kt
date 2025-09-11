package com.delighted2wins.souqelkhorda.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.delighted2wins.souqelkhorda.features.buyers.presentation.screen.NearestBuyersScreen
import com.delighted2wins.souqelkhorda.features.market.presentation.screen.MarketScreen
import com.delighted2wins.souqelkhorda.features.sale.presentation.screen.DirectSaleScreen
import com.delighted2wins.souqelkhorda.features.splash.SplashScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier, isSplashScreen: MutableState<Boolean>) {
    val backStack = rememberNavBackStack(SplashScreen)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                DirectSaleScreen -> {
                    NavEntry(key) {
                        DirectSaleScreen()
                    }
                }
                MarketScreen -> {
                    NavEntry(key) {
                        MarketScreen()
                    }
                }

                NearestBuyersScreen -> {
                    NavEntry(key) {
                        NearestBuyersScreen()
                    }
                }
                SplashScreen -> {
                    NavEntry(key) {
                        SplashScreen {
                            isSplashScreen.value = false
                            backStack.set(
                                element = DirectSaleScreen,
                                index = 0
                            )
                        }
                    }
                }
                else -> error("Unknown screen $key")
            }

        }
    )
}