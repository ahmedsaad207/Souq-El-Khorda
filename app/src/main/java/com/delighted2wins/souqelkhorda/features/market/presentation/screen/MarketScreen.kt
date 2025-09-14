package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.app.theme.Til
import com.delighted2wins.souqelkhorda.features.market.data.ScrapItem
import com.delighted2wins.souqelkhorda.features.market.data.ScrapStatus
import com.delighted2wins.souqelkhorda.features.market.data.User
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ScrapCard
import com.delighted2wins.souqelkhorda.features.market.presentation.component.SearchBar

@Composable
fun MarketScreen(
    onBuyClick: () -> Unit = {},
    onDetailsClick: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    val isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
    val users = sampleUser()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            Text(
                text = if (isRtl) "العروض المتاحة" else "Available Offers",
                style = MaterialTheme.typography.titleLarge,
                color = Til,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        items(
            sampleData().filter {
                it.title.contains(query, ignoreCase = true) || query.isBlank()
            }
        ) { scrapData ->
            val user = users.firstOrNull { it.id == scrapData.userId }

            ScrapCard(
                user = user ?: User(0, "مستخدم مجهول", "غير معروف"),
                scrapData,
                onBuyClick = { /* Handle buy action */ },
                onDetailsClick = { /* Handle details action */ },
                systemIsRtl  = isRtl,
            )
        }
    }
}


fun sampleData() = listOf(
    ScrapItem(1, "Plastic & Aluminum", "Mixed scrap materials...", "Cairo - Maadi", 25, quantity = 5,status = ScrapStatus.Available, date = "2025-09-10", userId = 100),
    ScrapItem(2, "ورق وكرتون مكتبي", "أوراق مكتبية متنوعة...", "Giza - Dokki", 30, quantity = 3, status = ScrapStatus.Sold, date = "2025-09-05", userId = 101),
    ScrapItem(3, "Iron Scrap", "Pieces of old iron...", "الإسكندرية", 50, quantity = 5, status = ScrapStatus.Waiting, date = "2025-09-12", userId = 102),
    ScrapItem(4, "Copper & Wires", "Used copper wires...", "طنطا", 15, status = ScrapStatus.Available, date = "2025-09-01", userId = 103),
    ScrapItem(5, "زجاج مستعمل", "زجاج معاد التدوير...", "Mansoura", 20, status = ScrapStatus.Reserved, date = "2025-09-02", userId = 104)
)

fun sampleUser() = listOf(
    User(100, "Ahmed Mohamed", "Cairo - Maadi", "https://avatar.iran.liara.run/public/boy?username=Scott"),
    User(101, "فاطمة أحمد", "Giza - Dokki", "https://avatar.iran.liara.run/public/girl?username=Maria"),
    User(102, "Mohamed Ali", "Alexandria", "https://avatar.iran.liara.run/public/boy?username=Scott"),
    User(103, "سارة محمود", "طنطا", "https://avatar.iran.liara.run/public/girl?username=Maria"),
    User(104, "Mohamed Mahmoud", "طنطا") // No image URL
)


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MarketScreenPreview() {
    MarketScreen()
}