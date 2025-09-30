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

    val categories: List<Pair<ScrapType, String?>> = scraps.map { scrap ->
        val type = ScrapType.fromCategory(scrap.category)
        type to scrap.category
    }.distinct()

    if (categories.isEmpty()) return

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEach { (scrapType, originalCategory) ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = if (scrapType == ScrapType.CustomScrap && !originalCategory.isNullOrBlank()) {
                        originalCategory  
                    } else {
                        scrapType.getLabel(context)
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}
