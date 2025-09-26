package com.delighted2wins.souqelkhorda.features.buyers.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cottage
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BuyerMainScreenCard(
    modifier: Modifier = Modifier,
    onMarketClick: ()->Unit ={},
    firstStatisticNumber: String = "5",
    secondStatisticNumber: String = "4",
    firstStatisticText: String = stringResource(R.string.all_buyer),
    secondStatisticText: String = stringResource(R.string.online_now),
    icon: ImageVector = Icons.Outlined.PeopleOutline,
    isFirstScreen: Boolean = true,
    cardTitle: String = stringResource(R.string.the_nearset_buyer),
    cardSubTitle: String =  stringResource(R.string.nearst_buyer_sub_title),
    firstSpanStyle: SpanStyle = SpanStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    ),
    secondSpanStyle: SpanStyle = SpanStyle(
        color = Color.White.copy(alpha = 0.8f),
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
    ) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    val layoutDirection = LocalLayoutDirection.current

    val gradient = if (layoutDirection == LayoutDirection.Rtl) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF2565E6),
                Color(0xFF1F7EA7),
                Color(0xFF00A9A2),
                Color(0xFF00B14F)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF00B14F),
                Color(0xFF00A9A2),
                Color(0xFF1F7EA7),
                Color(0xFF2565E6)
            )
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray.copy(0.5f)
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .background(gradient)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = firstSpanStyle,
                            block = {
                                append(
                                    cardTitle
                                )
                            }
                        )
                        withStyle(
                            style = secondSpanStyle,
                            block = {
                                append(
                                    cardSubTitle
                                )
                            }
                        )
                    },
                    textAlign = TextAlign.Center
                )
                if(isFirstScreen) {
                    CustomCartBtn(
                        onClick = {
                            onMarketClick()
                        },
                        imageVictor = Icons.Outlined.Cottage,
                        msg = stringResource(R.string.register_shop),
                        color = Color(0xff12a052),
                        btnWidth = screenWidth * 0.45,
                        textColor = Color.White,
                        fontSize = 12.0

                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                StatisticsCard(
                    numberText = firstStatisticNumber,
                    statisticsText = firstStatisticText,
                    width = screenWidth * 0.4,
                    numberTextStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White,
                    ),
                    statisticsTextStyle = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.7f),
                    ),
                )
                StatisticsCard(
                    numberText = secondStatisticNumber,
                    statisticsText = secondStatisticText,
                    width = screenWidth * 0.4,
                    numberTextStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White,
                    ),
                    statisticsTextStyle = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.7f),
                    ),
                )
            }

        }
    }
}
