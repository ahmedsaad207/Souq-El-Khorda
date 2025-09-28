package com.delighted2wins.souqelkhorda.features.market.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.enums.ScrapType
import com.delighted2wins.souqelkhorda.core.model.Scrap

@Composable
fun CategoryChips(scraps: List<Scrap>) {
    val context = LocalContext.current

    val categories: List<ScrapType> = scraps
        .mapNotNull { scrap ->
            ScrapType.entries.firstOrNull {
                it.name.equals(scrap.category, ignoreCase = true)
            }
        }
        .distinct()


    if (categories.isEmpty()) return

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEach { scrapType ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text =  scrapType.getLabel(context) ,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}