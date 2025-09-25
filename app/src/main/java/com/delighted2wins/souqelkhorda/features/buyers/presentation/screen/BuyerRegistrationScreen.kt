package com.delighted2wins.souqelkhorda.features.buyers.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Recycling
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.features.buyers.presentation.components.BuyerMainScreenCard
import com.delighted2wins.souqelkhorda.features.buyers.presentation.components.MarketingCard
import com.delighted2wins.souqelkhorda.features.buyers.presentation.components.RegisterBuyerForm

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BuyerRegistrationScreen(
    innerPadding: PaddingValues,
    snackBarHostState: SnackbarHostState,
    onBackClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        OneIconCard(
            onClick = onBackClick,
            headerTxt = stringResource(R.string.register_your_shop)
        )
        LazyColumn {
            item {
                Spacer(Modifier.height(16.dp))
                BuyerMainScreenCard(
                    firstStatisticNumber = "500",
                    secondStatisticNumber = "+1000",
                    firstStatisticText = stringResource(R.string.registerd_buyer),
                    secondStatisticText = stringResource(R.string.monthely_deal),
                    icon = Icons.Outlined.Recycling,
                    isFirstScreen = false,
                    cardTitle = stringResource(R.string.buyer_secoun_screen_title),
                    cardSubTitle = stringResource(R.string.buyer_secoun_screen_sub_title),
                    firstSpanStyle = SpanStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    secondSpanStyle = SpanStyle(
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(Modifier.height(16.dp))
            }
            item {
                Text(
                    stringResource(R.string.why_choose_us),
                    color = colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))

            }
            item {
                MarketingCard(
                    backgroundColor = Brush.linearGradient(
                        colors = listOf(
                            Color.Green.copy(alpha = 0.1f),
                            Color.Green.copy(alpha = 0.1f)
                        )
                    ),
                    icon = Icons.AutoMirrored.Outlined.TrendingUp,
                    titleText = stringResource(R.string.best_price),
                    subTitleText = stringResource(R.string.first_description),
                    isIcon = true,
                    iconColor = Color.Green,
                    iconText = null
                )
                Spacer(Modifier.height(16.dp))

            }
            item {
                MarketingCard(
                    backgroundColor = Brush.linearGradient(
                        colors = listOf(
                            Color.Blue.copy(alpha = 0.1f),
                            Color.Blue.copy(alpha = 0.1f)
                        )
                    ),
                    icon = Icons.Outlined.PeopleAlt,
                    titleText = stringResource(R.string.wide_domain),
                    subTitleText = stringResource(R.string.secound_description),
                    isIcon = true,
                    iconColor = Color.Blue,
                    iconText = null
                )
                Spacer(Modifier.height(16.dp))

            }
            item {
                MarketingCard(
                    backgroundColor = Brush.linearGradient(
                        colors = listOf(
                            Color.Magenta.copy(alpha = 0.1f),
                            Color.Magenta.copy(alpha = 0.1f)
                        )
                    ),
                    icon = Icons.Outlined.PrivacyTip,
                    titleText = stringResource(R.string.good_security),
                    subTitleText = stringResource(R.string.third_description),
                    isIcon = true,
                    iconColor = Color.Magenta,
                    iconText = null
                )
                Spacer(Modifier.height(16.dp))

            }
            item {
                Text(
                    stringResource(R.string.steps_ofRegister),
                    color = colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                MarketingCard(
                    backgroundColor = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1F7EA7),
                            Color(0xFF00A9A2),
                            Color(0xFF00B14F)
                        )
                    ),
                    icon = null,
                    titleText = stringResource(R.string.fill_data),
                    subTitleText = stringResource(R.string.fourth_description),
                    isIcon = false,
                    iconColor = Color.White,
                    iconText = "1"
                )
                Spacer(Modifier.height(16.dp))
                MarketingCard(
                    backgroundColor = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1F7EA7),
                            Color(0xFF00A9A2),
                            Color(0xFF00B14F)
                        )
                    ),
                    icon = null,
                    titleText = stringResource(R.string.identity_encurance),

                    subTitleText = stringResource(R.string.fifth_description),
                    isIcon = false,
                    iconColor = Color.White,
                    iconText = "2"
                )
                Spacer(Modifier.height(16.dp))
                MarketingCard(
                    backgroundColor = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1F7EA7),
                            Color(0xFF00A9A2),
                            Color(0xFF00B14F)
                        )
                    ),
                    icon = null,
                    titleText = stringResource(R.string.start_immediatly),
                    subTitleText = stringResource(R.string.sixsety_description),
                    isIcon = false,
                    iconColor = Color.White,
                    iconText = "3"
                )
                Spacer(Modifier.height(16.dp))

                RegisterBuyerForm()
                Spacer(Modifier.height(56.dp))

            }
        }
    }

}