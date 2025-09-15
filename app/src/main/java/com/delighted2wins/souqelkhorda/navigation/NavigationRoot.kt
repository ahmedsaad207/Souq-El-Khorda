package com.delighted2wins.souqelkhorda.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.delighted2wins.souqelkhorda.features.authentication.presentation.screen.SignUpScreen
import com.delighted2wins.souqelkhorda.features.buyers.presentation.screen.NearestBuyersScreen
import com.delighted2wins.souqelkhorda.features.login.presentation.screen.LoginScreen
import com.delighted2wins.souqelkhorda.features.market.presentation.screen.MarketScreen
import com.delighted2wins.souqelkhorda.features.sale.presentation.screen.DirectSaleScreen
import com.delighted2wins.souqelkhorda.features.splash.SplashScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    bottomBarState: MutableState<Boolean>,
    snackBarState: SnackbarHostState,
    backStack: NavBackStack
) {
    NavDisplay(
        modifier = modifier, backStack = backStack, entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        onBack ={
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = { key ->
            when (key) {
                DirectSaleScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        DirectSaleScreen()
                    }
                }

                MarketScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        MarketScreen()
                    }
                }

                NearestBuyersScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        NearestBuyersScreen()
                    }
                }

                SplashScreen -> {
                    NavEntry(key) {
                        SplashScreen {
                            bottomBarState.value = false
                            backStack.set(
                                element = LoginScreen, index = 0
                            )
                        }
                    }
                }

                LoginScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        LoginScreen(
                            onLoginClick = {
                                backStack.set(
                                    element = DirectSaleScreen, index = 0
                                )
                            },
                            onRegisterClick = {
                                backStack.add(SignUpScreen)
                            },
                            snackBarHostState = snackBarState,
                        )

                    }
                }

                SignUpScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        SignUpScreen(onBackClick = {
                            backStack.remove(SignUpScreen)
                            backStack.set(element =LoginScreen, index = 0)
                        }, snackBarHostState = snackBarState, onRegisterClick = {
                            backStack.remove(SignUpScreen)
                            backStack.set(element =LoginScreen, index = 0)
                        })

                    }
                }

                else -> error("Unknown screen $key")
            }

        })
}