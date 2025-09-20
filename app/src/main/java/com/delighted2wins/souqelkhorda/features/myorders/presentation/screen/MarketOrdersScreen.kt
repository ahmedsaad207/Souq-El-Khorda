package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarketOrdersScreen(
    mySellsCount: Int = 3,
    myOffersCount: Int = 1
) {
    var selectedFilter by remember { mutableStateOf("sells") }

    LaunchedEffect(selectedFilter) {
        when (selectedFilter) {
            "sells" -> {
                // viewModel.loadMySells()
            }
            "offers" -> {
                // viewModel.loadMyOffers()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = selectedFilter == "sells",
                onClick = { selectedFilter = "sells" },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "My Sells",
                            fontWeight = if (selectedFilter == "sells") FontWeight.Bold else FontWeight.Normal
                        )
                        if (mySellsCount > 0) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text(
                                    text = mySellsCount.toString(),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            )

            FilterChip(
                selected = selectedFilter == "offers",
                onClick = { selectedFilter = "offers" },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "My Offers",
                            fontWeight = if (selectedFilter == "offers") FontWeight.Bold else FontWeight.Normal
                        )
                        if (myOffersCount > 0) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text(
                                    text = myOffersCount.toString(),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Screen Content depending on filter
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (selectedFilter) {
                "sells" -> Text("Showing My Sells", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                "offers" -> Text("Showing My Offers", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
