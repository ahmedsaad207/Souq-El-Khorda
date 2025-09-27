package com.delighted2wins.souqelkhorda.core.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.delighted2wins.souqelkhorda.R


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun EmptyCart(resId: Int = R.raw.empty_cart, messageInfo : String){
    val config = LocalConfiguration.current
    val screenHeight =config.screenHeightDp
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            LottieAnimation(
                composition = composition.value,
                iterations = 1,
                modifier = Modifier.height((screenHeight*0.3).dp),
                renderMode = RenderMode.AUTOMATIC,
            )
            Text(messageInfo ,fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}