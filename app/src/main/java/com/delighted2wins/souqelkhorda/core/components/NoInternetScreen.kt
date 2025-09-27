package com.delighted2wins.souqelkhorda.core.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.delighted2wins.souqelkhorda.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun NoInternetScreen(padding: PaddingValues = PaddingValues()) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height((screenHeight * 0.2).dp))
        NoInternet()
        Text(
            stringResource(R.string.please_check_your_internet_connection),
            modifier = Modifier.height((screenHeight * 0.1).dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(Modifier.height((screenHeight * 0.2).dp))

    }

}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun NoInternet(){
    val config = LocalConfiguration.current
    val screenHeight =config.screenHeightDp
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet))
    Box{
        LottieAnimation(
            composition = composition.value,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.height((screenHeight*0.5).dp),
            renderMode = RenderMode.AUTOMATIC
        )
    }
}