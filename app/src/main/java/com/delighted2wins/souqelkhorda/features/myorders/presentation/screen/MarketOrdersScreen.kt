package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.EmptyCart
import com.delighted2wins.souqelkhorda.features.myorders.presentation.component.FilterWithBadge
import com.delighted2wins.souqelkhorda.features.myorders.presentation.component.OrdersDisplay
import com.delighted2wins.souqelkhorda.features.myorders.presentation.contract.MyOrdersState

@Composable
fun MarketOrdersScreen(
    state: MyOrdersState,
    onChipSelected: (String) -> Unit,
    onSaleDetailsClick: (String) -> Unit,
    onOfferDetailsClick: (String) -> Unit,
    onSaleOrderCancelClick: (String) -> Unit,
    systemIsRtl: Boolean
) {
    var selectedFilter by remember { mutableStateOf("Sells") }

    LaunchedEffect(selectedFilter) {
        onChipSelected(selectedFilter)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            FilterWithBadge(
                label = stringResource(R.string.sales_label),
                count = 0,
                selected = selectedFilter == "Sells",
                onClick = { selectedFilter = "Sells" }
            )

            FilterWithBadge(
                label = stringResource(R.string.offers_label),
                count = 0,
                selected = selectedFilter == "Offers",
                onClick = { selectedFilter = "Offers" }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            when (selectedFilter) {
                "Sells" -> {
                    if (state.sells.isEmpty() && !state.isLoading) {
                        Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                        {
                            EmptyCart(messageInfo = stringResource(R.string.no_sells_available))
                        }
                    } else {
                        OrdersDisplay(
                            orders = state.sells,
                            isLoading = state.isLoading,
                            error = state.error,
                            onDetailsClick = { order, _ ->
                                onSaleDetailsClick(order)
                            },
                            onCancelClick ={ orderId ->
                                onSaleOrderCancelClick(orderId)
                            }
                        )
                    }
                }

                "Offers" -> {
                    if (state.offers.isEmpty() && !state.isLoading) {
                        Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                        {
                           EmptyCart(messageInfo = stringResource(R.string.no_offers_available))
                        }
                    } else {
                        OrdersDisplay(
                            orders = state.offers,
                            isLoading = state.isLoading,
                            error = state.error,
                            onDetailsClick = { order, _ ->
                                onOfferDetailsClick(order)
                            },
                            onCancelClick ={}
                        )
                    }
                }
            }
        }

    }
}
