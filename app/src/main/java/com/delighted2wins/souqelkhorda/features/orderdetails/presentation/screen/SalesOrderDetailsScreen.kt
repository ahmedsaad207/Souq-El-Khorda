package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.AcceptedOfferItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderInformationCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OfferItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.ScrapItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.SectionTitle
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.SalesOrderDetailsViewModel

@Composable
fun SalesOrderDetailsScreen(
    orderId: String,
    snackBarHostState: SnackbarHostState,
    onChatClick: (orderId: String, sellerId: String, buyerId: String, offerId: String) -> Unit,
    onBackClick: () -> Unit = {},
    viewModel: SalesOrderDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isRtl = LocalLayoutDirection.current == androidx.compose.ui.unit.LayoutDirection.Rtl

    LaunchedEffect(orderId) {
        viewModel.onIntent(SalesOrderDetailsIntent.LoadOrderDetails(orderId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SalesOrderDetailsEffect.NavigateToChat -> {
                    onChatClick(effect.orderId, effect.sellerId, effect.buyerId, effect.offerId)
                }
                is SalesOrderDetailsEffect.ShowSuccess -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
                is SalesOrderDetailsEffect.ShowError -> {
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
                items(10) { ShimmerScrapCard(systemIsRtl = false) }
            }
        }

        state.order != null -> {
            SalesOrderDetailsUI(
                order = state.order!!,
                acceptedOffers = state.acceptedOffers,
                pendingOffers = state.pendingOffers,
                isRtl = isRtl,
                onBackClick = onBackClick,
                onChatClick = { sellerId, buyerId, orderId, offerId ->
                    viewModel.onIntent(
                        SalesOrderDetailsIntent.ChatWithBuyer(
                            sellerId,
                            buyerId,
                            orderId,
                            offerId
                        )
                    )
                },
                onCompleted = { offerId ->
                    viewModel.onIntent(SalesOrderDetailsIntent.CompleteOffer(offerId))
                },
                onCancel = { offerId ->
                    viewModel.onIntent(SalesOrderDetailsIntent.CancelOffer(offerId))
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
private fun SalesOrderDetailsUI(
    order: Order,
    isRtl: Boolean,
    acceptedOffers: List<Pair<Offer, MarketUser>>,
    pendingOffers: List<Pair<Offer, MarketUser>>,
    onBackClick: () -> Unit = {},
    onChatClick: (sellerId: String, buyerId: String, orderId: String, offerId: String) -> Unit,
    onCompleted: (offerId: String) -> Unit,
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
            SectionTitle(
                icon = Icons.Outlined.Inventory2,
                title = "Scraps",
                count = order.scraps.size,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

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

        if (acceptedOffers.isNotEmpty()) {
            item {
                SectionTitle(
                    icon = Icons.Filled.AttachMoney,
                    title = if (isRtl) "مناقشة العروض" else "Discussion Offers",
                    count = acceptedOffers.size,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            items(acceptedOffers) { (offer, user) ->
                AcceptedOfferItemCard(
                    buyer = user,
                    offer = offer,
                    onChat = { onChatClick(order.userId, offer.buyerId, offer.orderId, offer.offerId) },
                    onCompleted = { onCompleted(offer.offerId) },
                    onCancel = { onCancel(offer.offerId) }
                )
            }
        }

        if (pendingOffers.isNotEmpty()) {
            item {
                SectionTitle(
                    icon = Icons.Filled.AttachMoney,
                    title = if (isRtl) "العروض" else "Offers",
                    count = pendingOffers.size,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            items(pendingOffers) { (offer, user) ->
                OfferItemCard(
                    buyer = user,
                    offer = offer,
                    onAccept = { onCompleted(offer.offerId) },
                    onReject = { onCancel(offer.offerId) }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
