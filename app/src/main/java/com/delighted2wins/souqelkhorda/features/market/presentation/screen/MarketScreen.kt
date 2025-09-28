package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.EmptyCart
import com.delighted2wins.souqelkhorda.core.enums.BottomSheetActionType
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ScrapCard
import com.delighted2wins.souqelkhorda.features.market.presentation.component.SearchBar
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketEffect
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketIntent
import com.delighted2wins.souqelkhorda.features.offers.UserActionsBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    innerPadding: PaddingValues = PaddingValues(),
    snackBarHostState: SnackbarHostState,
    viewModel: MarketViewModel = hiltViewModel(),
    onDetailsClick: (String, String) -> Unit,
) {
    val state = viewModel.state
    val isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
    val pullToRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedOrder by remember { mutableStateOf<Order?>(null)}
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val retryLabel = stringResource(R.string.retry)

    LaunchedEffect(Unit) {
        viewModel.loadCurrentUser()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MarketEffect.NavigateToOrderDetails -> onDetailsClick(effect.orderId, effect.ownerId)
                is MarketEffect.ShowSuccess -> {
                    if (isBottomSheetVisible) {
                        sheetState.hide()
                        isBottomSheetVisible = false
                        selectedOrder = null
                    }
                    coroutineScope.launch { snackBarHostState.showSnackbar(effect.message) }
                }
                is MarketEffect.ShowError -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = effect.message,
                            actionLabel = retryLabel
                        ).let { result ->
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onIntent(MarketIntent.Refresh)
                            }
                        }
                    }
                }
            }
        }
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = state.isRefreshing,
        onRefresh = { viewModel.onIntent(MarketIntent.Refresh) },
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        when {
            state.isLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(10) { ShimmerScrapCard(systemIsRtl = isRtl) }
                }
            }

            state.error != null -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.error,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            state.isEmpty -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        EmptyCart(
                            messageInfo = stringResource(R.string.no_market_data)
                        )
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SearchBar(
                            query = state.query,
                            onQueryChange = {
                                viewModel.onIntent(MarketIntent.SearchQueryChanged(it))
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.available_offers),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                ),
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 4f),
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                            )
                        }
                    }

                    items(
                        state.successfulOrders.filter {
                            it.title.contains(state.query, ignoreCase = true) || state.query.isBlank()
                        }
                    ) { scrapData ->

                        var user by remember { mutableStateOf<MarketUser?>(null) }

                        LaunchedEffect(scrapData) {
                            viewModel.getUserData(scrapData.userId) { loadedUser ->
                                user = loadedUser
                            }
                        }

                        user?.let { loadedUser ->
                            ScrapCard(
                                currentUserId = viewModel.currentUser?.id.toString(),
                                marketUser = loadedUser,
                                order = scrapData,
                                onMakeOfferClick = {
                                    selectedOrder = scrapData
                                    isBottomSheetVisible = true
                                    coroutineScope.launch { sheetState.show() }
                                },
                                onDetailsClick = { orderId, ownerId -> onDetailsClick(orderId, ownerId)},
                            )
                        } ?: ShimmerScrapCard(systemIsRtl = isRtl)
                    }

                    item { Spacer(modifier = Modifier.padding(60.dp)) }
                }
            }
        }
    }

    if (isBottomSheetVisible && viewModel.currentUser != null) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    isBottomSheetVisible = false
                    selectedOrder = null
                }
            },
            sheetState = sheetState
        ) {
            UserActionsBottomSheet(
                orderId = selectedOrder!!.orderId,
                offerMaker = viewModel.currentUser,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                onConfirmAction = {
                    viewModel.onIntent(MarketIntent.MakeOffer(selectedOrder!!, it as Offer, selectedOrder!!.userId))
                },
                isSubmitting = viewModel.state.isSubmitting,
                isRtl = isRtl,
                actionType = BottomSheetActionType.MAKE_OFFER
            )
        }
    }
}
