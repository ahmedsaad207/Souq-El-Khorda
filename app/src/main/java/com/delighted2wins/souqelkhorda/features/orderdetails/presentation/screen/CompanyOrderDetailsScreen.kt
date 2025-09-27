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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.core.utils.generateUiOrderId
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.ScrapItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.SectionTitle
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.CompanyOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.CompanyOrderDetailsState
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel.CompanyOrderDetailsViewModel
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
                seller = uiState.seller,
                isRtl = false,
                onBackClick = onBackClick
            )
        }

        CompanyOrderDetailsState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No order details found")
            }
        }
    }
}

@Composable
private fun CompanyOrderDetailsUI(
    order: Order,
    seller: MarketUser?,
    isRtl: Boolean,
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            OrderDetailsTopBar(
                title = if (isRtl) "التفاصيل" else "Details",
                isRtl = isRtl,
                onBackClick = onBackClick
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Business,
                            contentDescription = "Company",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        DirectionalText(
                            text = if (isRtl) "شركة سوق الخردة" else "Souq El Khorda Company",
                            style = MaterialTheme.typography.titleLarge,
                            contentIsRtl = isRtl
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    DirectionalText(
                        text = if (isRtl) "العنوان: الاسكندرية، مصر" else "Address: Alexandria, Egypt",
                        style = MaterialTheme.typography.bodyMedium,
                        contentIsRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    DirectionalText(
                        text = if (isRtl) "البريد: info@souqelkhorda.com" else "Email: info@souqelkhorda.com",
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Person",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        DirectionalText(
                            text = if (isRtl) "معلومات البائع" else "Seller Information",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                            contentIsRtl = isRtl
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    DirectionalText(
                        text = seller?.name ?: "-",
                        style = MaterialTheme.typography.bodyLarge,
                        contentIsRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    DirectionalText(
                        text = seller?.location ?: "-",
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
                        icon = { Icon(Icons.Default.Receipt, contentDescription = "Order Id") },
                        label = if (isRtl) "معرف الطلب" else "Order ID",
                        value = generateUiOrderId(order.orderId, order.date),
                        isRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(
                        icon = { Icon(Icons.Default.DateRange, contentDescription = "Date") },
                        label = if (isRtl) "التاريخ" else "Date",
                        value = order.date.toFormattedDate(),
                        isRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(
                        icon = { Icon(Icons.Default.Receipt, contentDescription = "Status") },
                        label = if (isRtl) "الحالة" else "Status",
                        value = order.status.name,
                        isRtl = isRtl
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(
                        icon = {
                            Icon(
                                Icons.Default.Receipt,
                                contentDescription = "Price",
                            )
                        },
                        label = if (isRtl) "السعر" else "Price",
                        value = if (order.price == 0) "-" else "${order.price} ${if (isRtl) "جنيه" else "EGP"}",
                        isRtl = isRtl
                    )
                }
            }
        }

        item {
            SectionTitle(
                icon = Icons.Outlined.Inventory2,
                title = "Scraps",
                count = order.scraps.size,
                modifier = Modifier.padding(horizontal = 12.dp)
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

@Composable
private fun ScrapItemRow(scrap: Scrap, isRtl: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (isRtl) Arrangement.End else Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Category",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))

//                DirectionalText(
//                    text = if (isRtl) "الفئة:" else "Category:",
//                    style = MaterialTheme.typography.labelMedium.copy(
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    ),
//                    contentIsRtl = isRtl
//                )
//                Spacer(modifier = Modifier.width(6.dp))
                DirectionalText(
                    text = scrap.category,
                    style = MaterialTheme.typography.bodyLarge,
                    contentIsRtl = isRtl
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            DirectionalText(
                text = (if (isRtl) "الكمية: " else "Amount: ") + "${scrap.amount} ${scrap.unit}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                contentIsRtl = isRtl
            )

            if (scrap.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                DirectionalText(
                    text = (if (isRtl) "الوصف: " else "Description: ") + scrap.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    contentIsRtl = isRtl
                )
            }

            if (scrap.images.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                DirectionalText(
                    text = if (isRtl) "الصور:" else "Images:",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    contentIsRtl = isRtl
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(scrap.images) { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Scrap Image",
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

private fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}
