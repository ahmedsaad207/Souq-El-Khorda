package com.delighted2wins.souqelkhorda.features.orderdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.ActionButtonsSection
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.DescriptionSection
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderDetailsTopBar
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.OrderItemCard
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.SellerInfoSection

@Composable
fun OrderDetailsScreen(
    order: Order,
    user: MarketUser,
    onBackClick: () -> Unit = {},
) {
    val isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
   // var marketUser by remember { mutableStateOf<MarketUser?>(null) }

    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ){
            item {
                OrderDetailsTopBar(
                    onBackClick = onBackClick,
                    title = order.title,
                    isRtl = isRtl
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SellerInfoSection(
                            userImage = user.imageUrl,
                            userName = user.name,
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        ActionButtonsSection()
                    }
                }
            }

            item {
                DescriptionSection(
                    order.description,
                    isRtl = isRtl
                )
            }

            item {
                DirectionalText(
                    text = if (isRtl) "تفاصيل الأصناف" else "Order Items",
                    contentIsRtl = isRtl,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth().padding(start = 6.dp, end = 16.dp, top = 8.dp)
                )
            }

            items(order.scraps) { item ->
                OrderItemCard(
                    item = item,
                    contentIsRtl = isRtl,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

