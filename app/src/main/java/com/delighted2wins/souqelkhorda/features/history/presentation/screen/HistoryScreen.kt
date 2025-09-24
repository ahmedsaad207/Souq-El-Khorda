package com.delighted2wins.souqelkhorda.features.history.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.extensions.convertNumbersToArabic
import com.delighted2wins.souqelkhorda.core.extensions.toFormattedDate
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistoryCard
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistorySummaryCard
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistoryTabs
import com.delighted2wins.souqelkhorda.features.history.presentation.contract.HistoryContract
import com.delighted2wins.souqelkhorda.features.history.presentation.viewmodel.HistoryViewModel


@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onViewDetailsClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        OneIconCard(
            modifier = Modifier
                .background(colors.secondary)
                .padding(vertical = 16.dp),
            onClick = onBackClick,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            headerTxt = stringResource(R.string.transaction_history)
        )

        HistorySummaryCard(
            stats = listOf(
                Triple(state.completedCount.toString().convertNumbersToArabic(), OrderStatus.COMPLETED.getLocalizedValue(), OrderStatus.COMPLETED.color),
                Triple(state.pendingCount.toString().convertNumbersToArabic(), OrderStatus.PENDING.getLocalizedValue(), OrderStatus.PENDING.color),
                Triple(state.cancelledCount.toString().convertNumbersToArabic(), OrderStatus.CANCELLED.getLocalizedValue(), OrderStatus.CANCELLED.color)
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp)
        ) {
            items(filteredOrders) { order ->
                HistoryCard(
                    title = order.title,
                    transactionType = order.type,
                    status = order.status,
                    date = order.date.toFormattedDate().convertNumbersToArabic(),
                    items = order.scraps,
                    expanded = false,
                    selectedTab = state.selectedTabIndex,
                    onViewDetails = { }
                )
            }
        }
    }
}

