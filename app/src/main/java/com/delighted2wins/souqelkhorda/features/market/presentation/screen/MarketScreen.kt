package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapItem
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapStatus
import com.delighted2wins.souqelkhorda.features.market.domain.entities.User
import com.delighted2wins.souqelkhorda.features.market.presentation.component.Market.ScrapCard
import com.delighted2wins.souqelkhorda.features.market.presentation.component.Market.SearchBar

@Composable
fun MarketScreen(
    innerPadding: PaddingValues = PaddingValues(),
    onBuyClick: () -> Unit = {},
    onDetailsClick: (Int) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    val isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
    val users = sampleUser()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding()),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    isRtl = isRtl
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
                onDetailsClick = {
                    onDetailsClick(scrapData.id)
                },
                systemIsRtl  = isRtl,
            )
        }
        item {
            Spacer(modifier = Modifier.padding(60.dp))
        }
    }
}


fun sampleData() = listOf(
    ScrapItem(
        id = 1,
        title = "Plastic & Aluminum",
        description = "Mixed scrap materials...",
        location = "Cairo - Maadi",
        price = 25.0,
        weight = 12,
        quantity = 5,
        status = ScrapStatus.Available,
        date = "2025-09-10",
        userId = 100
    ),
    ScrapItem(
        id = 2,
        title = "ورق وكرتون مكتبي",
        description = "أوراق مكتبية متنوعة...",
        location = "Giza - Dokki",
        price = 30.0,
        weight = 8,
        quantity = 3,
        status = ScrapStatus.Sold,
        date = "2025-09-05",
        userId = 101
    ),
    ScrapItem(
        id = 3,
        title = "Iron Scrap",
        description = "Pieces of old iron...",
        location = "الإسكندرية",
        price = 50.0,
        weight = 20,
        quantity = 5,
        status = ScrapStatus.Waiting,
        date = "2025-09-12",
        userId = 102
    ),
    ScrapItem(
        id = 4,
        title = "Copper & Wires",
        description = "Used copper wires...",
        location = "طنطا",
        price = 15.0,
        weight = 10,
        status = ScrapStatus.Available,
        date = "2025-09-01",
        userId = 103
    ),
    ScrapItem(
        id = 5,
        title = "زجاج مستعمل",
        description = "زجاج معاد التدوير...",
        location = "Mansoura",
        price = 20.0,
        weight = 7,
        status = ScrapStatus.Reserved,
        date = "2025-09-02",
        userId = 104
    ),
    ScrapItem(
        id = 6,
        title = "Wood Pieces",
        description = "Old wooden furniture parts...",
        location = "Cairo - Nasr City",
        price = 18.0,
        weight = 15,
        quantity = 4,
        status = ScrapStatus.Available,
        date = "2025-09-08",
        userId = 105
    ),
    ScrapItem(
        id = 7,
        title = "Electronic Boards",
        description = "Broken circuit boards...",
        location = "Giza - Haram",
        price = 40.0,
        weight = 6,
        quantity = 2,
        status = ScrapStatus.Waiting,
        date = "2025-09-11",
        userId = 106
    ),
    ScrapItem(
        id = 8,
        title = "Car Batteries",
        description = "Used car batteries for recycling...",
        location = "Cairo - Shubra",
        price = 60.0,
        weight = 25,
        quantity = 3,
        status = ScrapStatus.Sold,
        date = "2025-09-03",
        userId = 107
    ),
    ScrapItem(
        id = 9,
        title = "Textile Waste",
        description = "Fabric and textile leftovers...",
        location = "Alexandria - Sidi Gaber",
        price = 12.0,
        weight = 9,
        quantity = 6,
        status = ScrapStatus.Reserved,
        date = "2025-09-04",
        userId = 108
    ),
    ScrapItem(
        id = 10,
        title = "Mixed Metals",
        description = "Combination of various metals...",
        location = "Cairo - Helwan",
        price = 55.0,
        weight = 18,
        quantity = 5,
        status = ScrapStatus.Available,
        date = "2025-09-09",
        userId = 109
    )
)

fun sampleUser() = listOf(
    User(
        id = 100,
        name = "Ahmed Mohamed",
        location = "Cairo - Maadi",
        imageUrl = "https://avatar.iran.liara.run/public/boy?username=Ahmed"
    ),
    User(
        id = 101,
        name = "فاطمة أحمد",
        location = "Giza - Dokki",
        imageUrl = "https://avatar.iran.liara.run/public/girl?username=Fatma"
    ),
    User(
        id = 102,
        name = "Mohamed Ali",
        location = "Alexandria",
        imageUrl = "https://avatar.iran.liara.run/public/boy?username=Mohamed"
    ),
    User(
        id = 103,
        name = "سارة محمود",
        location = "Tanta",
        imageUrl = "https://avatar.iran.liara.run/public/girl?username=Sara"
    ),
    User(
        id = 104,
        name = "Mohamed Mahmoud",
        location = "Mansoura" // No image URL
    ),
    User(
        id = 105,
        name = "Youssef Adel",
        location = "Cairo - Nasr City",
        imageUrl = "https://avatar.iran.liara.run/public/boy?username=Youssef"
    ),
    User(
        id = 106,
        name = "Mona Hassan",
        location = "Giza - Haram",
        imageUrl = "https://avatar.iran.liara.run/public/girl?username=Mona"
    ),
    User(
        id = 107,
        name = "Omar Khaled",
        location = "Cairo - Shubra",
        imageUrl = "https://avatar.iran.liara.run/public/boy?username=Omar"
    ),
    User(
        id = 108,
        name = "Layla Ibrahim",
        location = "Alexandria - Sidi Gaber",
        imageUrl = "https://avatar.iran.liara.run/public/girl?username=Layla"
    ),
    User(
        id = 109,
        name = "Hassan Tarek",
        location = "Cairo - Helwan" // No image URL
    )
)



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MarketScreenPreview() {
    MarketScreen()
}