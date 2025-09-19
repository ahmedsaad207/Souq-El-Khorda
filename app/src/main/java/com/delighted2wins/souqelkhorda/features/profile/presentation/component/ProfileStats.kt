package com.delighted2wins.souqelkhorda.features.profile.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.app.theme.AppTypography

@Composable
fun ProfileStats(stats: List<Pair<String, String>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        stats.forEach { (value, label) ->
            HeaderStat(value, label)
        }
    }
}

@Composable
private fun HeaderStat(value: String, label: String) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = AppTypography.titleLarge, color = colors.primary)
        Text(text = label, style = typography.bodyMedium, color = colors.onSurfaceVariant)
    }
}
