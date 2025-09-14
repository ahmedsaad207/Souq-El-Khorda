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
                style = MaterialTheme.typography.titleMedium,
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
                isRtl = isRtl,
            )
        }
    }
}


fun sampleData() = listOf(
    ScrapItem(1, "بلاستيك وألومنيوم", "مجموعة متنوعة...", "القاهرة - المعادي", 25,status = ScrapStatus.Available, date = "2025-09-10", userId = 100),
    ScrapItem(2, "ورق وكرتون مكتبي", "أوراق مكتبية...", "الجيزة - الدقي", 30,status = ScrapStatus.Sold, date = "2025-09-5", userId = 101),
    ScrapItem(3, "حديد خردة", "قطع حديد...", "الإسكندرية", 50,status = ScrapStatus.Waiting, date = "2025-09-12", userId = 102),
    ScrapItem(4, "نحاس وأسلاك", "أسلاك نحاس...", "طنطا", 15,status = ScrapStatus.Available, date = "2025-09-1", userId = 103),
    ScrapItem(5, "زجاج مستعمل", "زجاج معاد...", "المنصورة", 20,status = ScrapStatus.Reserved, date = "2025-09-2", userId = 104)
)

fun sampleUser() = listOf(
    User(100, "أحمد محمد", "القاهرة - المعادي", "https://avatar.iran.liara.run/public/boy?username=Scott"),
    User(101, "فاطمة أحمد", "الجيزة - الدقي", "https://avatar.iran.liara.run/public/girl?username=Maria"),
    User(102, "محمد علي", "الإسكندرية", "https://avatar.iran.liara.run/public/boy?username=Scott"),
    User(103, "سارة محمود", "طنطا", "https://avatar.iran.liara.run/public/girl?username=Maria"),
    User(104, " محمد محمود", "طنطا"),
    )


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MarketScreenPreview() {
    MarketScreen()
}