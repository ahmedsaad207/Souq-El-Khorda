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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        marketUser?.let {
            UserSection(
                marketUserData = it,
                date = scrap.date.toFormattedDate(),
                systemIsRtl = systemIsRtl
            )
        }

        Column(modifier = Modifier.padding(12.dp)) {

            Column(modifier = Modifier.fillMaxWidth()) {

                DirectionalText(
                    text = scrap.title,
                    contentIsRtl = isArabic(scrap.title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    softWrap = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                DirectionalText(
                    text = scrap.description,
                    contentIsRtl = isArabic(scrap.description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DirectionalText(
                        text = scrap.date.toTimeAgo(systemIsRtl),
                        contentIsRtl = systemIsRtl,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )

                    DirectionalText(
                        text = if (systemIsRtl) "السعر: ${scrap.price} ج.م" else "Price: ${scrap.price} EGP",
                        contentIsRtl = systemIsRtl,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        ),
                        color = Color.Red,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }
            }


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
    }
}
