package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.enums.BottomSheetActionType
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.offers.UserActionsBottomSheet
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.DescriptionSection
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderSummaryCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.SellerInfoSection
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsState
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.MarketOrderDetailsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketOrderDetailsScreen(
    snackBarHostState: SnackbarHostState,
    orderId: String,
    orderOwnerId: String,
    onBackClick: () -> Unit,
    navToSellerProfile: () -> Unit,
    viewModel: MarketOrderDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == androidx.compose.ui.unit.LayoutDirection.Rtl

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MarketOrderDetailsEffect.ShowSuccess -> {
                    if (isBottomSheetVisible) {
                        sheetState.hide()
                        isBottomSheetVisible = false
                        selectedOrder = null
                    }
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message = effect.message)
                    }
                }

                is MarketOrderDetailsEffect.ShowError -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message = effect.message)
                    }
                }
            }
        }
    }

    LaunchedEffect(orderId, orderOwnerId) {
        viewModel.onIntent(MarketOrderDetailsIntent.LoadOrder(orderId, orderOwnerId))
    }

    val uiState = state.value
    val isSubmitting = (uiState as? MarketOrderDetailsState.Success)?.isSubmitting == true

    when (uiState) {
        is MarketOrderDetailsState.Loading -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10) { ShimmerScrapCard(systemIsRtl = isRtl) }
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
                currentUserId = currentUser!!.id,
                onBackClick = onBackClick,
                onMakeOfferClick = {
                    isBottomSheetVisible = true
                    selectedOrder = uiState.order
                },
                navToSellerProfile = navToSellerProfile
            )
        }

        MarketOrderDetailsState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.no_order_details_found))
            }
        }
    }

    if (isBottomSheetVisible && selectedOrder != null) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    isBottomSheetVisible = false
                    selectedOrder = null
                }
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            UserActionsBottomSheet(
                orderId = selectedOrder!!.orderId,
                offerMaker = currentUser,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                onConfirmAction = {
                    viewModel.onIntent(
                        MarketOrderDetailsIntent.MakeOffer(
                            selectedOrder!!,
                            it as Offer,
                            selectedOrder!!.userId
                        )
                    )
                },
                isSubmitting = isSubmitting,
                isRtl = isRtl,
                actionType = BottomSheetActionType.MAKE_OFFER
            )
        }
    }
}

@Composable
private fun MarketOrderDetailsUI(
    order: Order,
    currentUserId: String,
    orderOwner: MarketUser?,
    onBackClick: () -> Unit = {},
    onMakeOfferClick: () -> Unit = {},
    navToSellerProfile: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Transparent,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                OrderDetailsTopBar(
                    title = stringResource(R.string.details),
                    onBackClick = onBackClick
                )
            }

            item {
                SellerInfoSection(
                    userImage = orderOwner?.imageUrl,
                    userName = orderOwner?.name ?: stringResource(R.string.unknown_user),
                    location = orderOwner?.location ?: stringResource(R.string.unknown_location),
                    orderUserId = order.userId,
                    currentUserId = currentUserId,
                    onMakeOfferClick = onMakeOfferClick,
                    onProfileClick = navToSellerProfile
                )
            }
            item {
                DirectionalText(
                    text = order.title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                    contentIsRtl = false
                )
            }
            item {
                OrderSummaryCard(order = order)
            }

            item {
                DescriptionSection(description = order.description)
            }

            item {
                Text(
                    text = stringResource(R.string.order_items_label),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 4.dp)
                        .padding(horizontal = 6.dp)
                )
            }

            items(order.scraps) { item ->
                OrderItemCard(
                    item = item,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}