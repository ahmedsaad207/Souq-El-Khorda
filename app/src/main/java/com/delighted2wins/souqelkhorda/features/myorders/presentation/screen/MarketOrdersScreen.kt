package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.LayoutDirection
import com.delighted2wins.souqelkhorda.features.myorders.presentation.component.FilterWithBadge
import com.delighted2wins.souqelkhorda.features.myorders.presentation.component.OrdersDisplay
import com.delighted2wins.souqelkhorda.features.myorders.presentation.contract.MyOrdersState

@Composable
fun MarketOrdersScreen(
    state: MyOrdersState,
    onChipSelected: (String) -> Unit,
    onDetailsClick: (String, String) -> Unit,
    systemIsRtl: Boolean
) {
    var selectedFilter by remember { mutableStateOf("Sells") }

    LaunchedEffect(selectedFilter) {
        onChipSelected(selectedFilter)
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides if (systemIsRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterWithBadge(
                    label = if (systemIsRtl) "معروضاتي" else "Sells",
                    count = state.sellsCount,
                    selected = selectedFilter == "Sells",
                    onClick = { selectedFilter = "Sells" }
                )

                FilterWithBadge(
                    label = if (systemIsRtl) "عروضي" else "Offers",
                    count = state.offersCount,
                    selected = selectedFilter == "Offers",
                    onClick = { selectedFilter = "Offers" }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedFilter) {
                    "Sells" -> {
                        if (state.sells.isEmpty() && !state.isLoading) {
                            Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                            {
                                Text(
                                    if (systemIsRtl) "لا يوجد معروضات متاحة" else "No sells available",
                                    fontSize = 24.sp,
                                    color = Color.Gray
                                )
                            }
                        } else {
                            OrdersDisplay(
                                orders = state.sells,
                                isLoading = state.isLoading,
                                error = state.error,
                                onDetailsClick = onDetailsClick,
                                systemIsRtl = systemIsRtl
                            )
                        }
                    }

                    "Offers" -> {
                        if (state.offers.isEmpty() && !state.isLoading) {
                            Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                            {
                                Text(
                                    if (systemIsRtl) "لا يوجد عروض متاحة" else "No offers available",
                                    fontSize = 24.sp,
                                    color = Color.Gray
                                )
                            }
                        } else {
                            OrdersDisplay(
                                orders = state.offers,
                                isLoading = state.isLoading,
                                error = state.error,
                                onDetailsClick = onDetailsClick,
                                systemIsRtl = systemIsRtl
                            )
                        }
                    }
                }
            }
        }
    }
}
