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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderInformationCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OfferItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.ScrapItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.SectionTitle
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.SalesOrderDetailsViewModel

@Composable
fun SalesOrderDetailsUI(
    order: Order,
    isRtl: Boolean,
    onBackClick: () -> Unit = {},
    viewModel: SalesOrderDetailsViewModel = hiltViewModel(),
) {

    LaunchedEffect(order) {
        Log.d("SalesOrderDetailsUI", "Loading order details for order ID: ${order.orderId}")
        viewModel.loadOrderDetails(order.orderId)
    }

    val currentOrder by viewModel.orderState.collectAsState()
    val offers by viewModel.offersState.collectAsState()

    currentOrder?.let { order ->
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
                items(order.scraps) { scrap ->
                    ScrapItemCard(scrap = scrap)
                }
            }else{
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Scraps Item Found",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                SectionTitle(
                    icon = Icons.Filled.AttachMoney,
                    title = "Offers",
                    count = 0,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            if (offers.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isRtl) "لا يوجد عروض بعد" else "No Offers Found",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                item {
                    Text("tttttttttttttttttttttttttttttttttttttttttttt")
                }
                items(offers) { offer ->
//                    OfferItemCard(
//                        offer = offer,
//                        onAccept = { viewModel.acceptOffer(offer.offerId) },
//                        onReject = { viewModel.rejectOffer(offer.offerId) }
//                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
