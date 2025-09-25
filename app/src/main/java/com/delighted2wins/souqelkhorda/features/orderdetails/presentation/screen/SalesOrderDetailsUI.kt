package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OfferItem
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.ScrapItem
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.SalesOrderDetailsViewModel

@Composable
fun SalesOrderDetailsUI(
    order: Order,
    isRtl: Boolean,
    onBackClick: () -> Unit = {},
    viewModel: SalesOrderDetailsViewModel = hiltViewModel(),
){
    LaunchedEffect(Unit) {
        Log.d("Navigation", "Navigated to SalesOrderDetailsUI with orderId: ${order.orderId}")
    }

    LaunchedEffect(order) {
        Log.d("SalesOrderDetailsUI", "Loading order details for order ID: ${order.orderId}")
        viewModel.loadOrderDetails(order.orderId)
    }

    val currentOrder by viewModel.orderState.collectAsState()
    val offers by viewModel.offersState.collectAsState()

    currentOrder?.let { order ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Top Bar
            item {
                OrderDetailsTopBar(
                    title = if (isRtl) "التفاصيل" else "Details",
                    isRtl = isRtl,
                    onBackClick = onBackClick
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Order Info
            item {
                Text(text = order.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = order.description)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Price: ${order.price}", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Scraps
            if (order.scraps.isNotEmpty()) {
                item {
                    Text("Scraps:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(order.scraps) { scrap ->
                    ScrapItem(scrap)
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Offers
            item { Text("Offers:", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            if (offers.isEmpty()) {
                item {
                    Text("No offers yet", color = Color.Gray)
                }
            } else {
                items(offers) { offer ->
                    OfferItem(
                        offer = offer,
                        orderStatus = order.status,
                        onAccept = { viewModel.acceptOffer(offer.offerId) },
                        onReject = { viewModel.rejectOffer(offer.offerId) }
                    )
                }
            }
        }
    }
}
