package com.delighted2wins.souqelkhorda.features.profile.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.app.theme.AppTypography


@Composable
fun AnimatedStat(
    value: String?,
    label: String
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (value == null) {
            val alpha by rememberInfiniteTransition(label = "")
                .animateFloat(
                    initialValue = 0.3f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = ""
                )

            Text(
                text = "…",
                style = AppTypography.titleLarge,
                color = colors.onSurface.copy(alpha = alpha)
            )
        } else {
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                },
                label = ""
            ) { displayValue ->
                Text(
                    text = displayValue,
                    style = AppTypography.titleLarge,
                    color = colors.primary
                )
            }
        }

        Text(
            text = label,
            style = typography.bodyMedium,
            color = colors.onSurfaceVariant
        )
    }
}


@Composable
fun ProfileStats(
    stats: List<Pair<String?, String>> // null = loading
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        stats.forEach { (value, label) ->
            AnimatedStat(value, label)
        }
    }
}

