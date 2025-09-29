package com.delighted2wins.souqelkhorda.features.market.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.utils.isArabic
import com.delighted2wins.souqelkhorda.core.utils.toSinceString
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
) {
    val cornerRadius = RoundedCornerShape(16.dp)

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cornerRadius),
        shape = cornerRadius,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(brush = gradientBrush),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            marketUser?.let {
                UserSection(
                    marketUserData = it,
                    date = order.date.toSinceString(LocalContext.current),
                )
            }

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
            )

            ScrapActions(
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
private fun ScrapMetaInfo(status: String, price: Int) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
    ) {
        Text(
            text = context.getString(R.string.price_label, price),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 2f),
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
//        StatusChip(status = status)
    }
}


@Composable
private fun ScrapActions(
    currentUserId: String?,
    scrap: Order,
    onMakeOfferClick: () -> Unit,
    onDetailsClick: (String, String) -> Unit
) {
    val context = LocalContext.current
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
                context.getString(R.string.details_button),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        if (!currentUserId.isNullOrEmpty() && currentUserId != scrap.userId) {
            Button(
                onClick = onMakeOfferClick,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    context.getString(R.string.make_offer_button),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
