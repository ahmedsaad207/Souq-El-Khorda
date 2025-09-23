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
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.features.authentication.presentation.screen.LoginScreen
import com.delighted2wins.souqelkhorda.features.authentication.presentation.screen.SignUpScreen
import com.delighted2wins.souqelkhorda.features.buyers.presentation.screen.NearestBuyersScreen
import com.delighted2wins.souqelkhorda.features.history.presentation.screen.HistoryScreen
import com.delighted2wins.souqelkhorda.features.market.presentation.screen.MarketScreen
import com.delighted2wins.souqelkhorda.features.myorders.presentation.screen.OrdersScreen
import com.delighted2wins.souqelkhorda.features.notification.presentation.screen.NotificationsScreen
import com.delighted2wins.souqelkhorda.features.orderdetails.OrderDetailsScreen
import com.delighted2wins.souqelkhorda.features.profile.presentation.screen.ProfileScreen
import com.delighted2wins.souqelkhorda.features.sell.presentation.screen.SellScreen
import com.delighted2wins.souqelkhorda.features.splash.SplashScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    bottomBarState: MutableState<Boolean>,
    snackBarState: SnackbarHostState,
    backStack: NavBackStack,
    innerPadding: PaddingValues,
    navState: MutableState<Boolean>
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
                    navState.value = false
                    NavEntry(key) {
                        bottomBarState.value = true
                        SellScreen(innerPadding)
                    }
                }

                is MarketScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        MarketScreen(
                            innerPadding,
                            snackBarHostState = snackBarState,
                            onDetailsClick = { orderId, ownerId ->
                                backStack.add(
                                    OrderDetailsKey(
                                        orderId,
                                        ownerId,
                                        source = OrderSource.MARKET
                                    )
                                )
                            }
                        )
                    }
                }

                is OrderDetailsKey -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        OrderDetailsScreen(
                            orderId = key.orderId,
                            orderOwnerId = key.orderOwnerId,
                            orderBuyerId = key.orderBuyerId,
                            source = key.source,
                            onBackClick = { backStack.remove(key) }
                        )
                    }
                }

                is NearestBuyersScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        NearestBuyersScreen()
                    }
                }

                is SplashScreen -> {
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

                is LoginScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        LoginScreen(
                            onRegisterClick = {
                                backStack.add(SignUpScreen)
                            },
                            onLoginClick = {
                                backStack.set(
                                    element = DirectSaleScreen, index = 0
                                )
                            },
                            snackBarHostState = snackBarState,
                            innerPadding = innerPadding,
                        )

                    }
                }

                is SignUpScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        SignUpScreen(
                            onBackClick = {
                                backStack.remove(SignUpScreen)
                                backStack.set(element = LoginScreen, index = 0)
                            },
                            onRegisterClick = {
                                backStack.remove(SignUpScreen)
                                backStack.set(element = LoginScreen, index = 0)
                            },
                            snackBarHostState = snackBarState,
                            innerPadding = innerPadding
                        )
                    }
                }

                is ProfileScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        ProfileScreen(
                            onBackClick = { backStack.remove(key) },
                            onHistoryClick = { backStack.add(HistoryScreen) },
                            onLogoutClick = {
                                backStack.clear()
                                backStack.add(LoginScreen)
                            }
                        )
                    }
                }

                is OrdersScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        OrdersScreen(
                            innerPadding,
                        )
                    }
                }

                is NotificationsScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        NotificationsScreen(
                            onBackClick = { backStack.remove(key) },
                        )
                    }
                }

                is HistoryScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        HistoryScreen(
                            onBackClick = { backStack.remove(key) }
                        )
                    }
                }


                else -> error("Unknown screen $key")
            }
        })
}