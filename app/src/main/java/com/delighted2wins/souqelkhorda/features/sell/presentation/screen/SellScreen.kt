package com.delighted2wins.souqelkhorda.features.sell.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.components.CustomButton
import com.delighted2wins.souqelkhorda.core.components.CustomCard
import com.delighted2wins.souqelkhorda.core.enums.Destination
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.additem.presentation.AddItemIntent
import com.delighted2wins.souqelkhorda.features.additem.presentation.viewmodel.AddItemViewModel
import com.delighted2wins.souqelkhorda.features.sell.presentation.SaleIntent
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.ItemsSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.OrderDestinationSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.OrderDetailsSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.viewmodel.SellViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun SellScreen(
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: SellViewModel = hiltViewModel(),
    AddItemViewModel: AddItemViewModel = hiltViewModel(),
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val selectedDestination = remember { mutableStateOf(Destination.Company) }
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val price = remember { mutableStateOf(0) }
    val showError = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

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
            OrderDestinationSection(
                selectedDestination = selectedDestination,
            )
        }

        item {
            ItemsSection(uiState.data) {
                val scrap = Scrap(
                    category = "paper",
                    unit = "kg",
                    amount = 2.0,
                    description = "dummy description",
                    images = "https://media.wired.com/photos/593261cab8eb31692072f129/3:2/w_2560%2Cc_limit/85120553.jpg"
                )
                AddItemViewModel.processIntent(AddItemIntent.AddIntent(scrap))
            }

        }

        item {
            if (uiState.data.isNotEmpty()) {
                CustomCard(
                    title = "Order Details",
                    color = Color(0xFF9144d4)
                ) {
                    OrderDetailsSection(
                        title = title,
                        description = description,
                        price = price,
                        showError = showError,
                        selectedDestination = selectedDestination
                    )
                }

                CustomButton(
                    icon = Icons.Default.Sync,
                    contentDescription = "Submit Order",
                    text = "Submit Order",
                    isLoading = isLoading,
                    textLoading = "Submitting Order"
                ) {

                    if (title.value.isBlank()) {
                        showError.value = true
                        return@CustomButton
                    }

                    isLoading.value = true

                    val order = Order(
                        userId = "dummy user",
                        scraps = uiState.data,
                        type = if (selectedDestination.value == Destination.Company) OrderType.SALE else OrderType.MARKET,
                        title = title.value.trim(),
                        description = description.value.trim(),
                        price = price.value
                    )

                    scope.launch {
                        delay(1000)
                        isLoading.value = false

                        viewModel.processIntent(SaleIntent.SendOrder(order))
                        title.value = ""
                        description.value = ""
                    }

                }

                Spacer(Modifier.height(innerPadding.calculateBottomPadding()))
            }
        }
    }
}

