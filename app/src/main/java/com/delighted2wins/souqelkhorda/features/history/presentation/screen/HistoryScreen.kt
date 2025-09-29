package com.delighted2wins.souqelkhorda.features.history.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.EmptyCart
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.extensions.convertNumbersToArabic
import com.delighted2wins.souqelkhorda.core.extensions.toRelativeTime
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistoryCard
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistoryCardShimmer
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistorySummaryCard
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistoryTabs
import com.delighted2wins.souqelkhorda.features.history.presentation.contract.HistoryContract
import com.delighted2wins.souqelkhorda.features.history.presentation.viewmodel.HistoryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onViewDetailsClick: (orderId: String, orderOwnerId: String, typeFlag: String) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme
    val pullToRefreshState = rememberPullToRefreshState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        OneIconCard(
            modifier = Modifier
                .background(colors.secondary)
                .statusBarsPadding()
                .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
            onClick = onBackClick,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            headerTxt = stringResource(R.string.transaction_history)
        )

        HistorySummaryCard(
            stats = listOf(
                Triple(
                    state.completedCount.toString().convertNumbersToArabic(),
                    OrderStatus.COMPLETED.getLocalizedValue(),
                    OrderStatus.COMPLETED.color
                ),
                Triple(
                    state.pendingCount.toString().convertNumbersToArabic(),
                    OrderStatus.PENDING.getLocalizedValue(),
                    OrderStatus.PENDING.color
                ),
                Triple(
                    state.cancelledCount.toString().convertNumbersToArabic(),
                    OrderStatus.CANCELLED.getLocalizedValue(),
                    OrderStatus.CANCELLED.color
                )
            )
        )


        HistoryTabs(
            tabs = listOf(
                stringResource(R.string.all),
                stringResource(R.string.sale),
                stringResource(R.string.market)
            ),
            selectedIndex = state.selectedTabIndex,
            onTabSelected = { index ->
                viewModel.handleIntent(HistoryContract.Intent.FilterOrders(index))
            }
        )

        val filteredOrders = state.orders.filter { order ->
            when (state.selectedTabIndex) {
                1 -> order.type == OrderType.SALE
                2 -> order.type == OrderType.MARKET
                else -> true
            }
        }

        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.handleIntent(HistoryContract.Intent.LoadOrders) },
            modifier = Modifier.fillMaxSize(),
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = pullToRefreshState,
                    isRefreshing = state.isLoading,
                    containerColor = MaterialTheme.colorScheme.surface,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp)
            ) {
                when (state.isLoading) {
                    true -> items(5) {
                        HistoryCardShimmer()
                    }

                    false -> {
                        if (filteredOrders.isEmpty()) {
                            item {
                                EmptyCart(
                                    R.raw.no_data,
                                    stringResource(R.string.you_don_t_have_any_transactions_yet)
                                )
                            }
                        } else {
                            items(filteredOrders) { order ->
                                HistoryCard(
                                    title = order.title,
                                    subtitle = order.type.getLocalizedValue(),
                                    status = order.status,
                                    date = order.date.toRelativeTime(context),
                                    items = order.scraps,
                                    expanded = false,
                                    onViewDetails = {
                                        val typeFlag = when {
                                            order.type == OrderType.SALE -> "SALE"
                                            order.userId == state.userId -> "MY_ORDER"
                                            else -> "OFFER"
                                        }
                                        onViewDetailsClick(order.orderId, order.userId, typeFlag)
                                    }
                                )
                            }
                        }
                    }
                }

            }
        }

    }
}

