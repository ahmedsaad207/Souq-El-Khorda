package com.delighted2wins.souqelkhorda.features.profile.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun ProfileHeaderBackground() {
    val colors = MaterialTheme.colorScheme

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
    ) {
        val width = size.width
        val height = size.height

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(colors.secondary, colors.secondaryContainer)
            ),
            size = size
        )

        val wavePath = Path().apply {
            moveTo(0f, height * 0.85f)
            quadraticBezierTo(
                width * 0.25f, height,
                width * 0.5f, height * 0.85f
            )
            quadraticBezierTo(
                width * 0.75f, height * 0.7f,
                width, height * 0.85f
            )
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(wavePath, color = colors.background)
    }
}
