package com.delighted2wins.souqelkhorda.features.buyers.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.features.buyers.data.sampleBuyers
import com.delighted2wins.souqelkhorda.features.buyers.presentation.components.BuyerCard
import com.delighted2wins.souqelkhorda.features.buyers.presentation.components.BuyerMainScreenCard

@Composable
fun NearestBuyersScreen(innerPadding: PaddingValues,onBuyerClick:()->Unit={}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            item{
                Spacer(Modifier.height(16.dp))
                BuyerMainScreenCard(

                    onMarketClick = {
                        onBuyerClick()
                    }
                )
                Spacer(Modifier.height(16.dp))
            }
           items(sampleBuyers){
                BuyerCard(buyerObj = it)
            }
        }
    }
}