package com.delighted2wins.souqelkhorda.features.buyers.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.EmptyCart
import com.delighted2wins.souqelkhorda.features.buyers.presentation.components.BuyerCard
import com.delighted2wins.souqelkhorda.features.buyers.presentation.components.BuyerMainScreenCard
import com.delighted2wins.souqelkhorda.features.buyers.presentation.state.BuyerState
import com.delighted2wins.souqelkhorda.features.buyers.presentation.view_model.BuyerViewModel

@Composable
fun NearestBuyersScreen(
    innerPadding: PaddingValues,
    onBuyerClick: () -> Unit = {},
    viewModel: BuyerViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    val state by viewModel.nearestBuyers.collectAsStateWithLifecycle()
    val isBuyer by viewModel.isBuyerState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getNearstBuyers()
        viewModel.isBuyer()
        viewModel.message.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        when (state) {
            is BuyerState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is BuyerState.SuccessLoading -> {
                val buyers = (state as BuyerState.SuccessLoading).list
                LazyColumn {
                    item {
                        Spacer(Modifier.height(16.dp))
                        BuyerMainScreenCard(
                            onMarketClick = { onBuyerClick() },
                            isFirstScreen = !isBuyer
                        )
                        Log.d("TAG", "NearestBuyersScreen: $isBuyer")
                        Spacer(Modifier.height(16.dp))
                    }
                    if (buyers.isNotEmpty()) {
                        items(buyers) { buyer ->
                            BuyerCard(buyerObj = buyer)
                        }
                    } else {
                        item {
                            Box{
                                EmptyCart(
                                    messageInfo = stringResource(R.string.u_have_no_buyer)
                                )
                            }
                        }
                    }
                }
            }

            is BuyerState.Error -> {
                Text("Error: ${(state as BuyerState.Error).errorMsg}")
            }

            else -> Unit
        }
    }
}