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
import com.delighted2wins.souqelkhorda.features.authentication.presentation.screen.LoginScreen
import com.delighted2wins.souqelkhorda.features.authentication.presentation.screen.SignUpScreen
import com.delighted2wins.souqelkhorda.features.buyers.presentation.screen.BuyerRegistrationScreen
import com.delighted2wins.souqelkhorda.features.buyers.presentation.screen.NearestBuyersScreen
import com.delighted2wins.souqelkhorda.features.chat.presentation.screen.ChatScreen
import com.delighted2wins.souqelkhorda.features.history.presentation.screen.HistoryScreen
import com.delighted2wins.souqelkhorda.features.market.presentation.screen.MarketScreen
import com.delighted2wins.souqelkhorda.features.myorders.presentation.screen.OrdersScreen
import com.delighted2wins.souqelkhorda.features.notification.presentation.screen.NotificationsScreen
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen.CompanyOrderDetailsScreen
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen.MarketOrderDetailsScreen
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen.OffersOrderDetailsScreen
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen.SalesOrderDetailsScreen
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
    navState: MutableState<Boolean>,
    screenNameState: MutableState<String>
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
                    screenNameState.value = "Direct Sale"
                    NavEntry(key) {
                        bottomBarState.value = true
                        SellScreen(innerPadding)
                    }
                }

                is MarketScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        screenNameState.value = "Market"
                        MarketScreen(
                            innerPadding,
                            snackBarHostState = snackBarState,
                            onDetailsClick = { orderId, ownerId ->
                                backStack.add(
                                    MarketOrderDetailsKey(
                                        orderId,
                                        ownerId,
                                    )
                                )
                            }
                        )
                    }
                }

                is MarketOrderDetailsKey -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        MarketOrderDetailsScreen(
                            orderId = key.orderId,
                            orderOwnerId = key.orderOwnerId,
                            onBackClick = { backStack.remove(key) }
                        )
                    }
                }

                is CompanyOrderDetailsKey -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        CompanyOrderDetailsScreen(
                            orderId = key.orderId,
                            orderOwnerId = key.orderOwnerId,
                            onBackClick = { backStack.remove(key) }
                        )
                    }
                }

                is SalesOrderDetailsKey -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        SalesOrderDetailsScreen(
                            orderId = key.orderId,
                            snackBarHostState = snackBarState,
                            onChatClick = { orderId, sellerId, buyerId, offerId ->
                                backStack.add(ChatKey(orderId, sellerId, buyerId, offerId))
                            },
                            onBackClick = { backStack.remove(key) }
                        )
                    }
                }

                is OffersOrderDetailsKey -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        OffersOrderDetailsScreen(
                            snackBarHostState = snackBarState,
                            orderId = key.orderId,
                            onChatClick = { orderId, sellerId, buyerId, offerId ->
                                backStack.add(ChatKey(orderId, sellerId, buyerId, offerId))
                            },
                            onBackClick = { backStack.remove(key) }
                        )
                    }
                }

                is ChatKey -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        ChatScreen(
                            orderId = key.orderId,
                            sellerId = key.sellerId,
                            buyerId = key.buyerId,
                            offerId = key.offerId,
                            onBack = { backStack.remove(key) }
                        )
                    }
                }

                is NearestBuyersScreen -> {
                    NavEntry(key) {
                        bottomBarState.value = true
                        screenNameState.value = "Nearest Buyers"
                        NearestBuyersScreen(
                            innerPadding = innerPadding,
                            onBuyerClick = {
                                backStack.add(element = BuyerRegistration)
                            }
                        )
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
                        screenNameState.value = "Orders"
                        OrdersScreen(
                            innerPadding = innerPadding,
                            snackBarHostState = snackBarState,
                            onCompanyDetailsClick = { orderId, ownerId ->
                                backStack.add(
                                    CompanyOrderDetailsKey(orderId, ownerId)
                                )
                            },
                            onSaleDetailsClick = { orderId ->
                                backStack.add(
                                    SalesOrderDetailsKey(orderId)
                                )
                            },
                            onOfferDetailsClick = { orderId ->
                                backStack.add(
                                    OffersOrderDetailsKey(orderId)
                                )
                            }
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

                is BuyerRegistration -> {
                    NavEntry(key) {
                        bottomBarState.value = false
                        BuyerRegistrationScreen(
                            innerPadding = innerPadding,
                            snackBarHostState = snackBarState,
                            onBackClick = {
                                backStack.remove(key)
                            }
                        )
                    }
                }

                else -> error("Unknown screen $key")
            }
        })
}