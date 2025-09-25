package com.delighted2wins.souqelkhorda.features.market.presentation.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.core.utils.isArabic
import com.delighted2wins.souqelkhorda.core.utils.toFormattedDate
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.StatusChip
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.UserSection

@Composable
fun ScrapCard(
    currentUserId: String?,
    marketUser: MarketUser?,
    order: Order,
    onMakeOfferClick: () -> Unit = {},
    onDetailsClick: (String, String) -> Unit,
    systemIsRtl: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        marketUser?.let {
            UserSection(
                marketUserData = it,
                date = order.date.toFormattedDate(),
                systemIsRtl = systemIsRtl
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {

            ScrapTitle(order.title)

            Spacer(modifier = Modifier.height(8.dp))

            ScrapDescription(order.description)

            Spacer(modifier = Modifier.height(16.dp))


            if (order.scraps.isNotEmpty()) {
                CategoryChips(order.scraps)
                Spacer(modifier = Modifier.height(12.dp))
            }

            ScrapMetaInfo(
                status = order.status.name,
                price = order.price,
                systemIsRtl = systemIsRtl
            )

            ScrapActions(
                systemIsRtl = systemIsRtl,
                currentUserId = currentUserId,
                scrap = order,
                onMakeOfferClick = onMakeOfferClick,
                onDetailsClick = onDetailsClick
            )
        }
    }
}

@Composable
private fun ScrapTitle(title: String) {
    DirectionalText(
        text = title,
        contentIsRtl = isArabic(title),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun ScrapDescription(description: String) {
    DirectionalText(
        text = description,
        contentIsRtl = isArabic(description),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun ScrapMetaInfo(status: String, price: Int, systemIsRtl: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        DirectionalText(
            text = if (systemIsRtl) "السعر: $price ج.م" else "Price: $price EGP",
            contentIsRtl = systemIsRtl,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
        StatusChip(status = status)
    }
}


@Composable
private fun ScrapActions(
    systemIsRtl: Boolean,
    currentUserId: String?,
    scrap: Order,
    onMakeOfferClick: () -> Unit,
    onDetailsClick: (String, String) -> Unit
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides if (systemIsRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            OutlinedButton(
                onClick = { onDetailsClick(scrap.orderId, scrap.userId) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    if (systemIsRtl) "تفاصيل" else "Details",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            if (!currentUserId.isNullOrEmpty() && currentUserId != scrap.userId) {
                Button(
                    onClick = onMakeOfferClick,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = if (systemIsRtl) "قدم عرضك" else "Make Offer",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
