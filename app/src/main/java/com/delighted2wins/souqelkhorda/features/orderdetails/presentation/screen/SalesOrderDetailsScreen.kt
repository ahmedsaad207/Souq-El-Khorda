package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.enums.BottomSheetActionType
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.offers.UserActionsBottomSheet
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.AcceptedOfferItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OfferItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderInformationCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.ScrapItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.SectionTitle
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.SalesOrderDetailsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedOfferId by remember { mutableStateOf("") }
    var selectedOrderId by remember { mutableStateOf("") }
    var selectedBuyerId by remember { mutableStateOf("") }
    var actionType by remember { mutableStateOf<BottomSheetActionType?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

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
                    val lastAction =  actionType
                    if (isBottomSheetVisible) {
                        sheetState.hide()
                        isBottomSheetVisible = false
                        selectedOfferId = ""
                        selectedOrderId = ""
                        selectedBuyerId = ""
                        actionType = null
                    }
                    coroutineScope.launch {
                        if (lastAction != BottomSheetActionType.COMPLETE_ORDER) {
                            snackBarHostState.showSnackbar(message = effect.message)
                        }
                    }

                    if (lastAction == BottomSheetActionType.COMPLETE_ORDER) {
                        onBackClick()
                    }
                }
                is SalesOrderDetailsEffect.ShowError -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    if (isBottomSheetVisible && actionType != null) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }
                isBottomSheetVisible = false
                selectedOfferId = ""
                selectedOrderId = ""
                selectedBuyerId = ""
                actionType = null
            },
            sheetState = sheetState
        ) {
            UserActionsBottomSheet(
                orderId = selectedOrderId,
                offerMaker = null,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                actionType = actionType!!,
                isSubmitting = state.isSubmitting,
                isRtl = isRtl,
                onConfirmAction = {
                    when (actionType) {
                        BottomSheetActionType.ACCEPT_OFFER -> {
                            viewModel.onIntent(
                                SalesOrderDetailsIntent.AcceptOffer(selectedOfferId, selectedBuyerId)
                            )
                        }
                        BottomSheetActionType.REJECT_OFFER -> {
                            viewModel.onIntent(
                                SalesOrderDetailsIntent.RejectOffer(
                                    selectedOrderId, selectedOfferId, selectedBuyerId
                                )
                            )
                        }
                        BottomSheetActionType.DELETE_OFFER -> {
                            viewModel.onIntent(
                                SalesOrderDetailsIntent.CancelOffer(
                                    selectedOrderId, selectedOfferId, selectedBuyerId
                                )
                            )
                        }
                        BottomSheetActionType.COMPLETE_ORDER -> {
                            viewModel.onIntent(
                                SalesOrderDetailsIntent.CompleteOffer(
                                    selectedOrderId, selectedOfferId, selectedBuyerId
                                )
                            )
                        }
                        else -> {}
                    }
                }
            )
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
                onAccept = { offerId, buyerId ->
                    selectedOfferId = offerId
                    selectedBuyerId = buyerId
                    selectedOrderId = state.order!!.orderId
                    actionType = BottomSheetActionType.ACCEPT_OFFER
                    isBottomSheetVisible = true
                    coroutineScope.launch { sheetState.show() }
                },
                onReject = { orderId, offerId, buyerId ->
                    selectedOfferId = offerId
                    selectedBuyerId = buyerId
                    selectedOrderId = orderId
                    actionType = BottomSheetActionType.REJECT_OFFER
                    isBottomSheetVisible = true
                    coroutineScope.launch { sheetState.show() }
                },
                onCancel = { orderId, offerId, buyerId ->
                    selectedOfferId = offerId
                    selectedBuyerId = buyerId
                    selectedOrderId = orderId
                    actionType = BottomSheetActionType.DELETE_OFFER
                    isBottomSheetVisible = true
                    coroutineScope.launch { sheetState.show() }
                },
                onCompleted = { orderId, offerId, buyerId ->
                    selectedOfferId = offerId
                    selectedBuyerId = buyerId
                    selectedOrderId = orderId
                    actionType = BottomSheetActionType.COMPLETE_ORDER
                    isBottomSheetVisible = true
                    coroutineScope.launch { sheetState.show() }
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
    onAccept: (offerId: String, buyerId: String) -> Unit,
    onReject: (orderId: String, offerId: String, buyerId: String) -> Unit,
    onCompleted: (orderId: String, offerId: String, buyerId: String) -> Unit,
    onCancel: (orderId: String, offerId: String, buyerId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            OrderDetailsTopBar(
                title = order.title,
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
                    onCompleted = { onCompleted(offer.orderId,offer.offerId, offer.buyerId) },
                    onCancel = { onCancel(offer.orderId, offer.offerId, offer.buyerId) }
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
                    onAccept = { onAccept(offer.offerId, offer.buyerId) },
                    onReject = { onReject(offer.orderId, offer.offerId, offer.buyerId) }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
