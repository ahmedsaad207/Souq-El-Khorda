package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.ActionButtonsSection
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.DescriptionSection
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.SellerInfoSection
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsState
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.MarketOrderDetailsViewModel

@Composable
fun MarketOrderDetailsScreen(
    orderId: String,
    orderOwnerId: String,
    onBackClick: () -> Unit,
    viewModel: MarketOrderDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(orderId, orderOwnerId) {
        viewModel.onIntent(MarketOrderDetailsIntent.LoadOrder(orderId, orderOwnerId))
    }

    when (val uiState = state.value) {
        is MarketOrderDetailsState.Loading -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10) { ShimmerScrapCard(systemIsRtl = false) }
            }
        }

        is MarketOrderDetailsState.Error -> {
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
                            text = uiState.message,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        is MarketOrderDetailsState.Success -> {
            MarketOrderDetailsUI(
                order = uiState.order,
                orderOwner = uiState.owner,
                isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl,
                onBackClick = onBackClick
            )
        }

        MarketOrderDetailsState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No order details found")
            }
        }
    }
}

@Composable
private fun MarketOrderDetailsUI(
    order: Order,
    orderOwner: MarketUser?,
    isRtl: Boolean,
    onBackClick: () -> Unit = {}
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                OrderDetailsTopBar(
                    onBackClick = onBackClick,
                    title = order.title,
                    isRtl = isRtl
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SellerInfoSection(
                            userImage = orderOwner?.imageUrl,
                            userName = orderOwner?.name ?: "User No Found",
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        ActionButtonsSection()
                    }
                }
            }

            item {
                DescriptionSection(
                    description = order.description,
                    isRtl = isRtl
                )
            }

            item {
                DirectionalText(
                    text = if (isRtl) "تفاصيل الأصناف" else "Order Items",
                    contentIsRtl = isRtl,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp, end = 16.dp, top = 8.dp)
                )
            }

            items(order.scraps) { item ->
                OrderItemCard(
                    item = item,
                    contentIsRtl = isRtl,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}