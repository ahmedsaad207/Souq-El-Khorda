package com.delighted2wins.souqelkhorda.features.splash

import android.annotation.SuppressLint
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
import androidx.hilt.navigation.compose.hiltViewModel
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
        if (viewModel.isLoggedIn()){
        navToHome()
        }else{
            navToLogin()
        }
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