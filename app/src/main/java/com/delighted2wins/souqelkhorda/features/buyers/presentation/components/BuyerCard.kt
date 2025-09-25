package com.delighted2wins.souqelkhorda.features.buyers.presentation.components

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PhoneEnabled
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.features.buyers.data.model.BuyerDto

@SuppressLint("ConfigurationScreenWidthHeight", "QueryPermissionsNeeded", "UseKtx")
@Composable
fun BuyerCard(modifier: Modifier = Modifier, buyerObj: BuyerDto) {

    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    val layoutDirection = detectLayoutDirection(buyerObj.name)

    val colors = MaterialTheme.colorScheme
    val list = buyerObj.types
    val context = LocalContext.current
    val onCallClick = {
        val phoneNumber = buyerObj.phone
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = ("tel:$phoneNumber").toUri()
        context.startActivity(intent)
    }
    val onDirectionClick = {
        try {
            val gmmIntentUri =
                "google.navigation:q=${buyerObj.latitude},${buyerObj.longitude}".toUri()
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                val browserUri =
                    "https://www.google.com/maps/search/?api=1&query=${buyerObj.latitude},${buyerObj.longitude}".toUri()
                context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color.LightGray.copy(0.5f)
            ),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = colors.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        buyerObj.name,
                        color = colors.onSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        buyerObj.governorate,
                        color = colors.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(4.dp))

                Row {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null
                    )

                    Text(
                        buyerObj.address,
                        color = colors.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    stringResource(R.string.accepted_types),
                    color = colors.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                ) {
                    list.forEach { type ->
                        InfoChip(
                            text = type
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (layoutDirection == LayoutDirection.Ltr) {
                        CustomCartBtn(
                            onClick = {
                                onDirectionClick()
                            },
                            imageVictor = Icons.Outlined.LocationOn,
                            msg = stringResource(R.string.direction),
                            color = colors.secondaryContainer,
                            btnWidth = screenWidth * 0.4,
                            textColor = Color.White

                        )
                        CustomCartBtn(
                            onClick = {
                                onCallClick()
                            },
                            imageVictor = Icons.Outlined.PhoneEnabled,
                            msg = stringResource(R.string.call),
                            color = colors.onSurface,
                            btnWidth = screenWidth * 0.4
                        )

                    } else {

                        CustomCartBtn(
                            onClick = {
                                onCallClick()
                            },
                            imageVictor = Icons.Outlined.PhoneEnabled,
                            msg = stringResource(R.string.call),
                            color = colors.onSurface,
                            btnWidth = screenWidth * 0.4
                        )
                        CustomCartBtn(
                            onClick = {
                                onDirectionClick()
                            },
                            imageVictor = Icons.Outlined.LocationOn,
                            msg = stringResource(R.string.direction),
                            color = colors.secondaryContainer,
                            btnWidth = screenWidth * 0.4,
                            textColor = Color.White

                        )
                    }
                }

            }
        }
    }
}


fun detectLayoutDirection(text: String): LayoutDirection {
    val firstChar = text.firstOrNull()
    return if (firstChar != null && firstChar in '\u0600'..'\u06FF') {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }
}
