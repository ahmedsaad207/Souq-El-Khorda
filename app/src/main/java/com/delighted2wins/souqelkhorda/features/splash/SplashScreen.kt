package com.delighted2wins.souqelkhorda.features.splash

import android.annotation.SuppressLint
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.features.authentication.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    navToHome: () -> Unit,
    navToLogin: () -> Unit,
) {
    LaunchedEffect(Unit) {
        delay(2000)
        if (viewModel.isLoggedIn()) {
            navToHome()
        } else {
            navToLogin()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        AnimatedBorderCard(
            content = {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    modifier = modifier
                        .size(130.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )
            },
            gradient = Brush.sweepGradient(
                listOf(
                    Color(0xFF2E7D32),
                    Color(0xFF81C784),
                    Color.White,
                    Color(0xFF2E7D32)
                )
            )
        )

    }
}


@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    radius: Int = 16,
    borderWidth: Dp = 2.dp,
    easing: Easing = LinearEasing,
    gradient: Brush = Brush.sweepGradient(listOf(Color.Gray, Color.White)),
    animationDuration: Int = 5000,
    onCardClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = easing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    Surface(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(radius.dp)
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .padding(borderWidth)
                .drawBehind {
                    rotate(degrees) {
                        drawCircle(
                            brush = gradient,
                            radius = size.minDimension / 2,
                            style = Stroke(width = borderWidth.toPx())
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}