package com.delighted2wins.souqelkhorda.features.authentication.presentation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun DotLoadingIndicator() {
    val dots = listOf(remember { Animatable(0.8f) },
        remember { Animatable(0.8f) },
        remember { Animatable(0.8f) })

    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(300),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        dots.forEach { scale ->
            Text(
                text = "•",
                modifier = Modifier.scale(scale.value),
                fontSize = 24.sp
            )
            Spacer(Modifier.width(4.dp))
        }
        Spacer(Modifier.width(8.dp))
    }
}