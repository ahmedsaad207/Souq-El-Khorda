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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.enums.BottomSheetActionType
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.offers.UserActionsBottomSheet
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.*
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.OffersOrderDetailsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var actionType by remember { mutableStateOf<BottomSheetActionType?>(null) }
    var selectedOfferId by remember { mutableStateOf("") }
    var selectedOrderId by remember { mutableStateOf("") }

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
                    val lastAction = actionType

                    if (isBottomSheetVisible) {
                        sheetState.hide()
                        isBottomSheetVisible = false
                        selectedOfferId = ""
                        selectedOrderId = ""
                        actionType = null
                    }

                    coroutineScope.launch {
                        if (lastAction != BottomSheetActionType.DELETE_OFFER) {
                            snackBarHostState.showSnackbar(message = effect.message)
                        }
                    }

                    if (lastAction == BottomSheetActionType.DELETE_OFFER) {
                        onBackClick()
                    }

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
                onUpdateOfferClick = {
                    selectedOfferId = state.buyerOffer!!.first.offerId
                    selectedOrderId = state.order!!.orderId
                    actionType = BottomSheetActionType.UPDATE_STATUS_OFFER
                    isBottomSheetVisible = true
                    coroutineScope.launch { sheetState.show() }
                },
                onBackClick = onBackClick,
                onChatClick = onChatClick,
                onMarkReceived = { offerId ->
                    selectedOfferId = offerId
                    selectedOrderId = state.order!!.orderId
                    actionType = BottomSheetActionType.MARK_RECEIVED
                    isBottomSheetVisible = true
                    coroutineScope.launch { sheetState.show() }
                },
                onCancel = { offerId ->
                    selectedOfferId = offerId
                    selectedOrderId = state.order!!.orderId
                    actionType = BottomSheetActionType.DELETE_OFFER
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

    if (isBottomSheetVisible && actionType != null) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }
                isBottomSheetVisible = false
                selectedOfferId = ""
                selectedOrderId = ""
                actionType = null
            },
            sheetState = sheetState
        ) {
            UserActionsBottomSheet(
                orderId = selectedOrderId,
                offerMaker = state.buyerOffer?.second,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                actionType = actionType!!,
                isSubmitting = state.isSubmitting,
                isRtl = isRtl,
                onConfirmAction = { data ->
                    when (actionType) {
                        BottomSheetActionType.UPDATE_STATUS_OFFER -> {
                            if (data is Offer) {
                                viewModel.onIntent(
                                    OffersOrderDetailsIntent.UpdateOffer(
                                        offerId = selectedOfferId,
                                        newPrice = data.offerPrice.toString(),
                                        sellerId = state.order!!.userId
                                    )
                                )
                            }
                        }

                        BottomSheetActionType.MARK_RECEIVED -> {
                            viewModel.onIntent(
                                OffersOrderDetailsIntent.MarkAsReceived(
                                    orderId = selectedOrderId,
                                    offerId = selectedOfferId,
                                    sellerId = state.order!!.userId
                                )
                            )
                        }

                        BottomSheetActionType.DELETE_OFFER -> {
                            viewModel.onIntent(
                                OffersOrderDetailsIntent.CancelOffer(
                                    orderId = selectedOrderId,
                                    offerId = selectedOfferId,
                                    sellerId = state.order!!.userId
                                )
                            )
                        }
                        else -> {}
                    }
                }
            )
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
    onUpdateOfferClick: () -> Unit = {},
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
                    onUpdate = { onUpdateOfferClick() },
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
