package com.delighted2wins.souqelkhorda.features.sale.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScrapCategoriesGrid(onCategoryClick: (String) -> Unit) {

    val categories = listOf(
        Triple("Aluminum", Icons.Default.Settings, Color(0xFF8a5ee6)),
        Triple("Plastic", Icons.Default.Settings, Color(0xFF497ddb)),
        Triple("Glass", Icons.Default.Settings, Color(0xFF17abcb)),
        Triple("Paper", Icons.Default.Settings, Color(0xFFe6981f)),
        Triple("Add custom item", Icons.Default.Add, Color(0xFF37c070))
    )

    FlowRow(
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEach { (label, icon, color) ->
            ScrapItem(
                label = label,
                icon = icon,
                backgroundColor = color
            ) {
                onCategoryClick(label)
            }
        }
    }
}