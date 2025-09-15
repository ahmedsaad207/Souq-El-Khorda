package com.delighted2wins.souqelkhorda.features.sale.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.delighted2wins.souqelkhorda.features.sale.presentation.components.ScrapItem

@Composable
fun DirectSaleScreen(
    innerPadding: PaddingValues = PaddingValues(),
    onCategoryClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = innerPadding.calculateTopPadding())
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Choose the items you want to sell",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Scrap categories",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                ScrapItem(
                    label = "Aluminum",
                    icon = Icons.Default.Settings,
                    backgroundColor = Color(0xFF8a5ee6)
                ) {
                    onCategoryClicked("Aluminum")

                    Log.i("TAG", "DirectSaleScreen: item clicked")
                }
            }

            item {
                ScrapItem(
                    label = "Plastic",
                    icon = Icons.Default.Settings,
                    backgroundColor = Color(0xFF497ddb)
                ) {

                    Log.i("TAG", "DirectSaleScreen: item clicked")
                }
            }

            item {
                ScrapItem(
                    label = "Glass",
                    icon = Icons.Default.Settings,
                    backgroundColor = Color(0xFF17abcb)
                ) {
                    Log.i("TAG", "DirectSaleScreen: item clicked")
                }
            }

            item {
                ScrapItem(
                    label = "Paper",
                    icon = Icons.Default.Settings,
                    backgroundColor = Color(0xFFe6981f)
                ) {
                    Log.i("TAG", "DirectSaleScreen: item clicked")
                }
            }

            item {
                ScrapItem(
                    label = "Add custom item",
                    icon = Icons.Default.Add,
                    backgroundColor = Color(0xFF37c070)
                ) {
                    Log.i("TAG", "DirectSaleScreen: item clicked")
                }
            }
        }
    }
}

