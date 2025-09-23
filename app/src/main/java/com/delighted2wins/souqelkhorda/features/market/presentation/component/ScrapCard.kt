package com.delighted2wins.souqelkhorda.features.market.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.delighted2wins.souqelkhorda.core.utils.isArabic
import com.delighted2wins.souqelkhorda.core.utils.toFormattedDate
import com.delighted2wins.souqelkhorda.core.utils.toTimeAgo
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component.UserSection

@Composable
fun ScrapCard(
    currentUserId: String?,
    marketUser: MarketUser?,
    scrap: Order,
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
                date = scrap.date.toFormattedDate(),
                systemIsRtl = systemIsRtl
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {

            ScrapTitle(scrap.title)

            Spacer(modifier = Modifier.height(8.dp))

            ScrapDescription(scrap.description)

            Spacer(modifier = Modifier.height(16.dp))

            ScrapMetaInfo(
                date = scrap.date.toTimeAgo(systemIsRtl),
                price = scrap.price,
                systemIsRtl = systemIsRtl
            )

            ScrapActions(
                systemIsRtl = systemIsRtl,
                currentUserId = currentUserId,
                scrap = scrap,
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
private fun ScrapMetaInfo(date: String, price: Int, systemIsRtl: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        DirectionalText(
            text = date,
            contentIsRtl = systemIsRtl,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )

        DirectionalText(
            text = if (systemIsRtl) "السعر: $price ج.م" else "Price: $price EGP",
            contentIsRtl = systemIsRtl,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
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
