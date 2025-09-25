package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
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
fun SalesOrderDetailsUI(
    order: Order,
    isRtl: Boolean,
    onBackClick: () -> Unit = {},
    viewModel: SalesOrderDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(order.orderId) {
        viewModel.onIntent(SalesOrderDetailsIntent.LoadOrderDetails(order.orderId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SalesOrderDetailsEffect.NavigateToChat -> {
                    // navigate to chat screen
                }
                is SalesOrderDetailsEffect.ShowError -> {
                    // show error message
                }
                else -> {}
            }
        }
    }

    state.order?.let { order ->
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
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Scraps Item Found", color = Color.Gray)
                    }
                }
            }

            if (state.acceptedOffers.isNotEmpty()) {
                item {
                    SectionTitle(
                        icon = Icons.Filled.AttachMoney,
                        title = if (isRtl) "مناقشة العروض" else "Discussion Offers",
                        count = state.acceptedOffers.size,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                items(state.acceptedOffers) { (offer, user) ->
                    AcceptedOfferItemCard(
                        buyer = user,
                        offer = offer,
                        onChat = { viewModel.onIntent(SalesOrderDetailsIntent.ChatWithBuyer(order.userId, offer.buyerId, offer.orderId)) },
                        onCompleted = { viewModel.onIntent(SalesOrderDetailsIntent.CompleteOffer(offer.offerId)) },
                        onCancel = { viewModel.onIntent(SalesOrderDetailsIntent.CancelOffer(offer.offerId)) }
                    )
                }
            }

            if (state.pendingOffers.isNotEmpty()) {
                item {
                    SectionTitle(
                        icon = Icons.Filled.AttachMoney,
                        title = if (isRtl) "العروض" else "Offers",
                        count = state.pendingOffers.size,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                items(state.pendingOffers) { (offer, user) ->
                    OfferItemCard(
                        buyer = user,
                        offer = offer,
                        onAccept = { viewModel.onIntent(SalesOrderDetailsIntent.AcceptOffer(offer.offerId)) },
                        onReject = { viewModel.onIntent(SalesOrderDetailsIntent.RejectOffer(offer.offerId)) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
