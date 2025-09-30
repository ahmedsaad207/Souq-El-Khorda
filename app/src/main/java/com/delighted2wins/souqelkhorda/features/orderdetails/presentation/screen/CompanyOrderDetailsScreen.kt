package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.components.EmptyCart
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.ScrapItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.SectionTitle
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.CompanyOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.CompanyOrderDetailsState
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.CompanyOrderDetailsViewModel
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.ScrapItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CompanyOrderDetailsScreen(
    orderId: String,
    orderOwnerId: String,
    onBackClick: () -> Unit,
    viewModel: CompanyOrderDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(orderId, orderOwnerId) {
        viewModel.onIntent(CompanyOrderDetailsIntent.LoadOrder(orderId, orderOwnerId))
    }

    when (val uiState = state.value) {
        is CompanyOrderDetailsState.Loading -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10) { ShimmerScrapCard(false) }
            }
        }

        is CompanyOrderDetailsState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is CompanyOrderDetailsState.Success -> {
            CompanyOrderDetailsUI(
                order = uiState.order,
                isRtl = false,
                onBackClick = onBackClick
            )
        }

        CompanyOrderDetailsState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EmptyCart(messageInfo = stringResource(R.string.no_orders_found))
            }
        }
    }
}

@Composable
private fun CompanyOrderDetailsUI(
    order: Order,
    isRtl: Boolean,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            OrderDetailsTopBar(
                title = stringResource(R.string.details),
                onBackClick = onBackClick
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Business,
                            contentDescription = "Company",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        DirectionalText(
                            text = stringResource(R.string.company_name),
                            style = MaterialTheme.typography.titleLarge,
                            contentIsRtl = isRtl
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    DirectionalText(
                        text = stringResource(R.string.company_address),
                        style = MaterialTheme.typography.bodyMedium,
                        contentIsRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    DirectionalText(
                        text = stringResource(R.string.company_email),
                        style = MaterialTheme.typography.bodyMedium,
                        contentIsRtl = isRtl
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SummaryRow(
                        icon = {
                            Icon(
                                Icons.Default.Receipt,
                                contentDescription = "Order Id",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        label = stringResource(R.string.order_id_label),
                        value = order.orderId,
                        isRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(
                        icon = {
                            Icon(
                                 Icons.Default.DateRange,
                                 contentDescription = "Date",
                                 tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        label = stringResource(R.string.date_label),
                        value = order.date.toFormattedDate(),
                        isRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(
                        icon = {
                            Icon(
                                Icons.Default.Receipt,
                                contentDescription = "Status",
                                tint = MaterialTheme.colorScheme.primary
                            )
                       },
                        label = stringResource(R.string.status),
                        value = order.status.name,
                        isRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(
                        icon = {
                            Icon(
                                Icons.Default.Receipt,
                                contentDescription = "Price",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        label = "",
                        value = context.getString(R.string.price_label, order.price),
                        isRtl = isRtl
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Note",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = stringResource(R.string.company_review_order),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                    )
                }
            }
        }

        item {
            SectionTitle(
                icon = Icons.Outlined.Inventory2,
                title = context.getString(R.string.scraps),
                count = order.scraps.size,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

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
                    Text(
                        stringResource(R.string.no_scraps_found),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SummaryRow(
    icon: @Composable () -> Unit,
    label: String,
    value: String,
    isRtl: Boolean,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        icon()
        Spacer(modifier = Modifier.width(8.dp))
        DirectionalText(
            text = "$label: $value",
            style = MaterialTheme.typography.bodyLarge,
            contentIsRtl = isRtl
        )
    }
}

private fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}