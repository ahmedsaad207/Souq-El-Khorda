package com.delighted2wins.souqelkhorda.features.myorders.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FilterWithBadge(
    label: String,
    count: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.secondary
            else
                MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.secondary,
            selectedContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.75f),
        ),
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
                if (count > 0) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Badge(
                        containerColor = if (selected) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Text(
                            text = count.toString(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        },
        shape = MaterialTheme.shapes.medium
    )
}
