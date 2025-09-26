package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.*
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.OffersOrderDetailsViewModel

@Composable
fun OffersOrderDetailsScreen(
    snackBarHostState: SnackbarHostState,
    orderId: String,
    onChatClick: (orderId: String, sellerId: String, buyerId: String, offerId: String) -> Unit,
    onBackClick: () -> Unit = {},
    viewModel: OffersOrderDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isRtl = LocalLayoutDirection.current == androidx.compose.ui.unit.LayoutDirection.Rtl
    var scrapsExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(orderId) {
        viewModel.onIntent(OffersOrderDetailsIntent.LoadOrderDetails(orderId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is OffersOrderDetailsEffect.NavigateToChat -> {
                    onChatClick(effect.orderId, effect.sellerId, effect.buyerId, effect.offerId)
                }
                is OffersOrderDetailsEffect.ShowSuccess -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
                is OffersOrderDetailsEffect.ShowError -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    when {
        state.isLoading -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(10) { ShimmerScrapCard(systemIsRtl = isRtl) }
            }
        }

        state.order != null -> {
            OffersOrderDetailsUI(
                order = state.order!!,
                scrapsExpanded = scrapsExpanded,
                onScrapsToggle = { scrapsExpanded = !scrapsExpanded },
                buyerOffer = state.buyerOffer,
                isRtl = isRtl,
                onBackClick = onBackClick,
                onChatClick = onChatClick,
                onMarkReceived = { offerId -> viewModel.onIntent(
                    OffersOrderDetailsIntent.MarkAsReceived(orderId, offerId, state.order!!.userId)
                    )
                },
                onCancel = { offerId -> viewModel.onIntent(
                    OffersOrderDetailsIntent.CancelOffer(orderId, offerId, state.order!!.userId)
                    )
                }
            )
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isRtl) "لا توجد بيانات" else "No data available",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun OffersOrderDetailsUI(
    order: Order,
    scrapsExpanded: Boolean,
    onScrapsToggle: () -> Unit,
    buyerOffer: Pair<Offer, MarketUser>?,
    isRtl: Boolean,
    onBackClick: () -> Unit = {},
    onChatClick: (orderId: String, sellerId: String, buyerId: String, offerId: String) -> Unit,
    onMarkReceived: (offerId: String) -> Unit,
    onCancel: (offerId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            OrderDetailsTopBar(
                title = if (isRtl) "التفاصيل" else "Details",
                isRtl = isRtl,
                onBackClick = onBackClick
            )
        }

        buyerOffer?.let { (offer, seller) ->
            item {
                SectionTitle(
                    icon = Icons.Filled.AttachMoney,
                    title = if (isRtl) "عرضي" else "My Offer",
                    count = 0,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            item {
                BuyerOfferCard(
                    seller = seller,
                    offer = offer,
                    onChat = { onChatClick(order.orderId, seller.id, offer.buyerId, offer.offerId) },
                    onMarkReceived = { onMarkReceived(offer.offerId) },
                    onCancel = { onCancel(offer.offerId) }
                )
            }
        }

        item {
            OrderInformationCard(
                title = order.title,
                description = order.description,
                price = order.price.toString(),
                scraps = order.scraps,
                status = order.status,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onScrapsToggle() }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SectionTitle(
                    icon = Icons.Outlined.Inventory2,
                    title = "Scraps",
                    count = order.scraps.size
                )
                Icon(
                    imageVector = if (scrapsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (scrapsExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        if (scrapsExpanded) {
            if (order.scraps.isNotEmpty()) {
                items(order.scraps) { scrap -> ScrapItemCard(scrap = scrap) }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Scraps Item Found", color = Color.Gray)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
