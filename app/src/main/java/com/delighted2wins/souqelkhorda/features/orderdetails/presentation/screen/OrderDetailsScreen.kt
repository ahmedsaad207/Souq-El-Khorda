package com.delighted2wins.souqelkhorda.features.orderdetails

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OrderDetailsState
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen.CompanyOrderDetailsUI
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen.MarketOrderDetailsUI
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen.SalesOrderDetailsUI
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.OrderDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    orderId: String,
    orderOwnerId: String,
    orderBuyerId: String? = null,
    source: OrderSource,
    viewModel: OrderDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
) {
    val layoutDirectionRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val orderOwner by viewModel.orderOwner.collectAsStateWithLifecycle()
    val orderBuyer by viewModel.orderBuyer.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        Log.d("Navigation", "Navigated to OrderDetailsScreen with orderId: $orderId")
    }

    LaunchedEffect(orderId, orderOwnerId, orderBuyerId) {
        Log.d("OrderDetailsScreen", "Loading order details for order ID: $orderId")
        Log.d("OrderDetailsScreen", "Loading order owner ID: $orderOwnerId")
        Log.d("OrderDetailsScreen", "Loading order buyer ID: $orderBuyerId")

        viewModel.onIntent(
            OrderDetailsIntent.LoadOrderDetails(
                orderId,
                orderOwnerId,
                orderBuyerId,
                source
            )
        )
    }

    LaunchedEffect(orderOwnerId) {
        viewModel.getUserData(orderOwnerId)
    }

    LaunchedEffect(orderBuyerId) {
        if (orderBuyerId != null) {
            viewModel.getUserData(orderBuyerId)
        }
    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {
        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = uiState is OrderDetailsState.Refreshing,
            onRefresh = { viewModel.onIntent(OrderDetailsIntent.Refresh) },
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is OrderDetailsState.Loading -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(10) { ShimmerScrapCard(systemIsRtl = layoutDirectionRtl) }
                    }
                }

                is OrderDetailsState.Error -> {
                    val error = (uiState as OrderDetailsState.Error).message
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
                                    text = error,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }

                is OrderDetailsState.Success -> {
                    RenderSuccess(
                        state = uiState as OrderDetailsState.Success,
                        orderOwner = orderOwner,
                        orderBuyer = orderBuyer,
                        isRtl = layoutDirectionRtl,
                        onBackClick = onBackClick
                    )
                }

                OrderDetailsState.Empty -> {
                    Log.d("OrderDetailsScreen", "Empty state reached")
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text =  if (layoutDirectionRtl) "لا توجد بيانات" else "No data available",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }

                OrderDetailsState.Refreshing -> {
                    viewModel.cachedSuccess?.let { success ->
                        RenderSuccess(
                            state = success,
                            orderOwner = orderOwner,
                            isRtl = layoutDirectionRtl,
                            onBackClick = onBackClick
                        )
                    } ?:
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(10) { ShimmerScrapCard(systemIsRtl = layoutDirectionRtl) }
                    }
                }
            }
        }
    }
}

@Composable
private fun RenderSuccess(
    state: OrderDetailsState.Success,
    orderOwner: MarketUser?,
    orderBuyer: MarketUser? = null,
    isRtl: Boolean,
    onBackClick: () -> Unit
) {
    when (state) {
        is OrderDetailsState.Success.Market -> {
            MarketOrderDetailsUI(state.order,orderOwner, isRtl, onBackClick)
        }
        is OrderDetailsState.Success.Company -> {
            CompanyOrderDetailsUI(state.order,orderOwner, isRtl, onBackClick)
        }
        is OrderDetailsState.Success.Sales -> {
           SalesOrderDetailsUI(state.order, isRtl, onBackClick)
        }
        is OrderDetailsState.Success.Offers -> {
          //  OffersOrderDetailsUI(state.order, state.buyerOffer, isRtl, onBackClick)
        }
    }
}