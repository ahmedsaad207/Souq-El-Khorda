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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.features.market.data.ScrapItem
import com.delighted2wins.souqelkhorda.features.market.data.User

@Composable
fun ScrapCard(
    user: User,
    scrap: ScrapItem,
    onBuyClick: () -> Unit = {},
    onDetailsClick: () -> Unit = {},
    isRtl: Boolean
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {

                ScrapUserSection(
                    userData = user,
                    status = scrap.status,
                    isRtl = isRtl
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = scrap.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = scrap.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isRtl) "الوزن: ${scrap.weight} كجم" else "Weight: ${scrap.weight} Kg",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onDetailsClick,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isRtl) "تفاصيل" else "Details")
                    }
                    Button(
                        onClick = onBuyClick,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isRtl) "شراء" else "Buy")
                    }
                }
            }
        }
    }
}


