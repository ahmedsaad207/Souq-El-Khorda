package com.delighted2wins.souqelkhorda.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navToHome: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        navToHome()
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.team),
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds
        )
    }
}