package com.delighted2wins.souqelkhorda.features.history.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistoryCard
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistorySummaryCard
import com.delighted2wins.souqelkhorda.features.history.presentation.components.HistoryTabs


@Preview(showBackground = true)
@Composable
fun PreviewHistoryCard() {
    HistoryCard(
        title = "Plastic Bottles Sale",
        transactionType = "Market",
        status = "Completed",
        date = "Dec 15, 2025",
        items = listOf("15x Plastic Bottles", "8x Aluminum Cans", "2x Glass Jars"),
        expanded = true,
        onExpandToggle = {},
        onViewDetails = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHistoryScreen() {
    val dummyOrders = listOf(
        ScrapOrder(
            id = 1,
            title = "Plastic Bottles Sale",
            type = "Market",
            status = "Completed",
            dateCreated = "Dec 15, 2025",
            amount = "$12.50",
            items = listOf("15x Plastic Bottles", "8x Aluminum Cans", "2x Glass Jars")
        ),
        ScrapOrder(
            id = 2,
            title = "Cardboard Collection",
            type = "Sale",
            status = "Pending",
            dateCreated = "Dec 14, 2025",
            amount = "$8.25",
            items = listOf("12x Large Cardboard Boxes", "5x Small Paper Bags")
        ),
        ScrapOrder(
            id = 3,
            title = "Organic Waste Sale",
            type = "Market",
            status = "Completed",
            dateCreated = "Dec 13, 2025",
            amount = "$8.75",
            items = listOf(
                "3kg Food Scraps",
                "2kg Garden Waste",
                "1kg Coffee Grounds",
                "0.5kg Fruit Peels"
            )
        )
    )

    HistoryScreen(
        orders = dummyOrders,
        onBackClick = {},
    )
}


@Composable
fun HistoryScreen(
    orders: List<ScrapOrder> = emptyList(),
    onBackClick: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme
    var selectedTab by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        OneIconCard(
            modifier = Modifier
                .background(colors.secondary)
                .padding(vertical = 16.dp),
            onClick = onBackClick,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            headerTxt = "Transaction History"
        )

        HistorySummaryCard(
            stats = listOf(
                Triple("24", "Completed", Color(0xFF00B259)),
                Triple("3", "Pending", Color(0xFFFE7E0F)),
                Triple("2", "Cancelled", Color(0xFFFF1111))
            )
        )


        HistoryTabs(
            tabs = listOf("All", "Sale", "Market"),
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp)
        ) {
            items(orders) { order ->
                HistoryCard(
                    title = order.title,
                    transactionType = if (order.type == "sale") "SALE" else "MARKET",
                    status = "COMPLETED",
                    date = order.dateCreated,
                    items = order.items.map { it.toString() },
                    expanded = false,
                    onExpandToggle = {},
                    onViewDetails = { }
                )
            }
        }
    }
}

data class ScrapOrder(
    val id: Int,
    val title: String,
    val type: String, // "Sale" or "Market"
    val status: String, // "Completed", "Pending", "Cancelled"
    val dateCreated: String,
    val amount: String,
    val items: List<String>
)
