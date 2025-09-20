package com.delighted2wins.souqelkhorda.navigation

import androidx.compose.foundation.layout.PaddingValues
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
import com.delighted2wins.souqelkhorda.features.additem.presentation.screen.AddItemScreen
import com.delighted2wins.souqelkhorda.features.authentication.presentation.screen.SignUpScreen
import com.delighted2wins.souqelkhorda.features.buyers.presentation.screen.NearestBuyersScreen
import com.delighted2wins.souqelkhorda.features.login.presentation.screen.LoginScreen
import com.delighted2wins.souqelkhorda.features.market.presentation.screen.MarketScreen
import com.delighted2wins.souqelkhorda.features.orderdetails.OrderDetailsScreen
import com.delighted2wins.souqelkhorda.features.myorders.presentation.screen.OrdersScreen
import com.delighted2wins.souqelkhorda.features.profile.presentation.screen.ProfileScreen
import com.delighted2wins.souqelkhorda.features.sale.presentation.screen.SaleScreen
import com.delighted2wins.souqelkhorda.features.splash.SplashScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    bottomBarState: MutableState<Boolean>,
    snackBarState: SnackbarHostState,
    backStack: NavBackStack,
    innerPadding: PaddingValues
) {
    NavDisplay(
        modifier = modifier, backStack = backStack, entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = { key ->
            when (key) {
                is DirectSaleScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        SaleScreen(innerPadding) {
                            backStack.add(element = AddItemKey(it))
                        }
                    }
                }

                is AddItemKey -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        AddItemScreen(key.category) {
                            backStack.removeLastOrNull()
                        }
                    }
                }

                MarketScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        MarketScreen(
                            innerPadding,
                            snackBarHostState = snackBarState,
                            navigateToMakeOffer = {
                                // Navigate to Buying Screen
                            },
                            onDetailsClick = { order ->
                                backStack.add(OrderDetailsKey(order))
                            },
                            navToAddItem = {
                               // backStack.add(AddItemKey(TODO()))
                            }
                        )
                    }
                }

                is OrderDetailsKey -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        OrderDetailsScreen(
                            order = key.order,
                            onBackClick = { backStack.remove(key) }
                        )
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
                        SplashScreen(
                            navToLogin = {
                                bottomBarState.value = false
                                backStack.set(
                                    element = LoginScreen, index = 0
                                )
                            },
                            navToHome = {
                                bottomBarState.value = true
                                backStack.set(
                                    element = DirectSaleScreen, index = 0
                                )

                            }
                        )
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
                            backStack.set(element = LoginScreen, index = 0)
                        }, snackBarHostState = snackBarState, onRegisterClick = {
                            backStack.remove(SignUpScreen)
                            backStack.set(element = LoginScreen, index = 0)
                        })

                    }
                }

                is ProfileScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        ProfileScreen(
                            onBackClick = { backStack.remove(key) },
                        )
                    }
                }

                OrdersScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        OrdersScreen(
                            innerPadding,
                            onBackClick = { backStack.remove(key) },
                        )
                    }
                }



                else -> error("Unknown screen $key")
            }

        })
}