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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.*
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.OffersOrderDetailsViewModel

@Composable
fun OffersOrderDetailsUI(
    snackBarHostState: SnackbarHostState,
    order: Order,
    isRtl: Boolean,
    onChatClick: (orderId: String, sellerId: String, buyerId: String) -> Unit,
    onBackClick: () -> Unit = {},
    viewModel: OffersOrderDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var scrapsExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(order.orderId) {
        viewModel.onIntent(OffersOrderDetailsIntent.LoadOrderDetails(order.orderId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is OffersOrderDetailsEffect.NavigateToChat -> {
                    onChatClick(effect.orderId, effect.sellerId, effect.buyerId)
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

                state.order?.userId?.let { sellerId ->
                    val sellerUser = state.buyerOffer?.second ?: return@let

                    item {
                        SellerCard(
                            seller = sellerUser,
                            offerStatus = state.buyerOffer?.first?.status,
                            onChat = {
                                viewModel.onIntent(
                                    OffersOrderDetailsIntent.ChatWithSeller(
                                        order.orderId,
                                        sellerUser.id,
                                        state.buyerOffer?.first?.buyerId ?: ""
                                    )
                                )
                            }
                        )
                    }
                }

                state.buyerOffer?.let { (offer, seller) ->
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
                            onChat = {
                                viewModel.onIntent(
                                    OffersOrderDetailsIntent.ChatWithSeller(
                                        offer.orderId,
                                        seller.id,
                                        offer.buyerId
                                    )
                                )
                            },
                            onMarkReceived = {
                                viewModel.onIntent(OffersOrderDetailsIntent.MarkAsReceived(offer.offerId))
                            },
                            onCancel = {
                                viewModel.onIntent(OffersOrderDetailsIntent.CancelOffer(offer.offerId))
                            }
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
                            .clickable { scrapsExpanded = !scrapsExpanded }
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
                        items(order.scraps) { scrap ->
                            ScrapItemCard(scrap = scrap)
                        }
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
