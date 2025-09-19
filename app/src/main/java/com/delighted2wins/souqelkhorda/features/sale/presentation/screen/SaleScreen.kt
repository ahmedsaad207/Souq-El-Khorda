package com.delighted2wins.souqelkhorda.features.sale.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order
import com.delighted2wins.souqelkhorda.features.sale.presentation.SaleIntent
import com.delighted2wins.souqelkhorda.features.sale.presentation.components.OrderSummarySection
import com.delighted2wins.souqelkhorda.features.sale.presentation.components.ScrapCategoriesGrid
import com.delighted2wins.souqelkhorda.features.sale.presentation.viewmodel.SaleViewModel

@Composable
fun SaleScreen(
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: SaleViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit,
) {

    val uiState = viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
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

            ScrapCategoriesGrid(onCategoryClick = onCategoryClick)
        }

        if (uiState.value.data.isNotEmpty()) {
            item {
                Spacer(Modifier.height(24.dp))

                val order = Order(
                    userId = "1",
                    scraps = uiState.value.data,
                    type = "SALE",
                    status = "PENDING",
                    time = System.currentTimeMillis(),
                )
                OrderSummarySection(
                    scraps = uiState.value.data,
                    onSend = { viewModel.processIntent(SaleIntent.SendOrder(order)) },
                    onCancel = { viewModel.processIntent(SaleIntent.CancelOrder) }
                )
            }
        }

        item {
            Spacer(Modifier.height(innerPadding.calculateBottomPadding()))
        }
    }
}

//userId
//scraps
//timestamp
//type-> sale
//state -> تحت التنفيض


