package com.delighted2wins.souqelkhorda.features.market.presentation.component.Market

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.delighted2wins.souqelkhorda.core.utils.isArabic
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import com.delighted2wins.souqelkhorda.features.market.domain.entities.User
import com.delighted2wins.souqelkhorda.features.market.presentation.component.OrderDetails.ScrapUserSection

@Composable
fun ScrapCard(
    user: User,
    scrap: ScrapOrder,
    onBuyClick: () -> Unit = {},
    onDetailsClick: (id:Int) -> Unit = {},
    systemIsRtl: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        ScrapUserSection(
            userData = user,
            date = scrap.date,
            systemIsRtl = systemIsRtl
        )

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
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    DirectionalText(
//                        text = if (systemIsRtl) "الوزن: ${scrap.weight} كجم" else "Weight: ${scrap.weight} Kg",
//                        contentIsRtl = systemIsRtl,
//                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
//                        color = MaterialTheme.colorScheme.primary,
//                        textAlign = TextAlign.Start,
//                        modifier = Modifier.weight(.6f)
//                    )
//
//                    scrap.quantity?.let {
//                        DirectionalText(
//                            text = if (systemIsRtl) "العدد: $it" else "Quantity: $it",
//                            contentIsRtl = systemIsRtl,
//                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
//                            color = MaterialTheme.colorScheme.primary,
//                            textAlign = TextAlign.Start,
//                            modifier = Modifier.weight(.6f)
//                        )
//                    }
//                    if (scrap.quantity == null) {
//                        Spacer(modifier = Modifier.weight(.6f))
//                    }

                    DirectionalText(
                        text = if (systemIsRtl) "السعر: ${scrap.price} ج.م" else "Price: ${scrap.price} EGP",
                        contentIsRtl = systemIsRtl,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        ),
                        color = Color.Red,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(.8f)
                    )
                }
            }


            CompositionLocalProvider(
                LocalLayoutDirection provides if (systemIsRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    OutlinedButton(
                        onClick = { onDetailsClick(scrap.id) },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            if (systemIsRtl) "تفاصيل" else "Details",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Button(
                        onClick = onBuyClick,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            if (systemIsRtl) "شراء" else "Buy",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
