package com.delighted2wins.souqelkhorda.features.buyers.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.features.buyers.data.sampleBuyers
import com.delighted2wins.souqelkhorda.features.buyers.presentation.components.BuyerCard

@Composable
fun NearestBuyersScreen(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            item{
                Spacer(Modifier.height(16.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(stringResource(R.string.nearest_buyer_title))
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append(stringResource(R.string.nearest_buyer_sub_title))
                        }
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

            }
           items(sampleBuyers){
                BuyerCard(buyerObj = it)
            }
        }
    }
}