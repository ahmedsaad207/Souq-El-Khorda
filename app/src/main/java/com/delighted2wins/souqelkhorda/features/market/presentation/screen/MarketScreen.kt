package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.delighted2wins.souqelkhorda.app.theme.Til
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrderItem
import com.delighted2wins.souqelkhorda.features.market.domain.entities.User
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ScrapCard
import com.delighted2wins.souqelkhorda.features.market.presentation.component.SearchBar
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketEffect
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketIntent

@Composable
fun MarketScreen(
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: MarketViewModel = hiltViewModel(),
    navigateToMakeOffer: () -> Unit = {},
    onDetailsClick: (ScrapOrder) -> Unit,
    navToAddItem: () -> Unit = {}
) {
    val state = viewModel.state
    val isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MarketEffect.NavigateToOrderDetails -> {
                    onDetailsClick(effect.order)
                }
                is MarketEffect.ShowError -> {
                    // TODO: show snackbar or dialog with effect.message
                }
                is MarketEffect.NavigateToSellNow -> {
                    navToAddItem()
                }
            }
        }
    }

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
                    query = state.query,
                    onQueryChange = { viewModel.onIntent(MarketIntent.SearchQueryChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    isRtl = isRtl
                )
            }
        }

        item {
            CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr)
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DirectionalText(
                        text = if (isRtl) "العروض المتاحة" else "Available Offers",
                        contentIsRtl = isRtl,
                        style = MaterialTheme.typography.titleLarge,
                        color = Til,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    OutlinedButton(
                        onClick = { viewModel.onIntent(MarketIntent.SellNowClicked) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Sell,
                                contentDescription = "sell now"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = if (isRtl) "بيع الأن" else "Sell Now")
                        }
                    }
                }
            }

        }

        items(
            state.scrapOrders.filter {
                it.date.contains(state.query, ignoreCase = true) || state.query.isBlank()
            }
        ) { scrapData ->
            ScrapCard(
                user = User(
                    id = scrapData.userId,
                    name = "User ${scrapData.userId}",
                    location = scrapData.location
                ),
                scrap = scrapData,
                onBuyClick = { navigateToMakeOffer() },
                onDetailsClick = { onDetailsClick(scrapData) },
                systemIsRtl = isRtl
            )
        }

        item {
            Spacer(modifier = Modifier.padding(60.dp))
        }
    }
}


fun sampleData() = listOf(
    ScrapOrder(
        id = 1,
        title = "Plastic & Aluminum",
        description = "Mixed scrap materials collected from households." +
                " Includes plastic bottles and aluminum cans." +
                " Ready for recycling." +
                " Contact for pickup arrangements." +
                " Thank you for supporting recycling efforts!" +
                " Let's make a greener planet together." +
                " Every bit counts!" +
                " Reduce, Reuse, Recycle!" +
                " Join us in our mission to promote sustainability.",
        location = "Cairo - Maadi",
        price = 25.0,
        date = "2025-09-10",
        userId = 100,
        items = listOf(
            ScrapOrderItem(
                id = 101,
                name = "Plastic Bottles",
                weight = 7,
                quantity = 3,
                images = listOf(
                    "https://picsum.photos/600/400?random=1",
                    "https://picsum.photos/600/400?random=2",
                    "https://picsum.photos/600/400?random=22",
                    "https://picsum.photos/600/400?random=23"
                )
            ),
            ScrapOrderItem(
                id = 102,
                name = "Aluminum Cans",
                weight = 5,
                quantity = 2,
                images = listOf(
                    "https://picsum.photos/600/400?random=3",
                    "https://picsum.photos/600/400?random=4"
                )
            ),
            ScrapOrderItem(
                id = 103,
                name = "Plastic Bags",
                weight = 2,
                quantity = null,
                images = emptyList()
            ),
            ScrapOrderItem(
                id = 104,
                name = "Plastic Containers",
                weight = 4,
                quantity = 4,
                images = listOf(
                    "https://picsum.photos/600/400?random=24"
                )
            ),
            ScrapOrderItem(
                id = 105,
                name = "Aluminum Foil",
                weight = 1,
                quantity = null,
                images = emptyList()
            )
        )
    ),
    ScrapOrder(
        id = 2,
        title = "ورق وكرتون مكتبي",
        description = "أوراق مكتبية وكرتون جاهزة لإعادة التدوير.",
        location = "Giza - Dokki",
        price = 30.0,
        date = "2025-09-05",
        userId = 101,
        items = listOf(
            ScrapOrderItem(
                id = 201,
                name = "Office Paper",
                weight = 5,
                quantity = null,
                images = listOf(
                    "https://picsum.photos/600/400?random=5",
                    "https://picsum.photos/600/400?random=6"
                )
            ),
            ScrapOrderItem(
                id = 202,
                name = "Cardboard",
                weight = 3,
                quantity = 3,
                images = listOf(
                    "https://picsum.photos/600/400?random=7"
                )
            )
        )
    ),
    ScrapOrder(
        id = 3,
        title = "Iron Scrap",
        description = "Heavy iron scrap collected from construction sites.",
        location = "Alexandria",
        price = 50.0,
        date = "2025-09-12",
        userId = 102,
        items = listOf(
            ScrapOrderItem(
                id = 301,
                name = "Iron Bars",
                weight = 20,
                quantity = 5,
                images = listOf(
                    "https://picsum.photos/600/400?random=8",
                    "https://picsum.photos/600/400?random=9"
                )
            )
        )
    ),
    ScrapOrder(
        id = 4,
        title = "Copper Wires",
        description = "Electric copper wires removed from old installations.",
        location = "Cairo - Nasr City",
        price = 80.0,
        date = "2025-09-13",
        userId = 103,
        items = listOf(
            ScrapOrderItem(
                id = 401,
                name = "Thick Copper Wire",
                weight = 10,
                quantity = 2,
                images = listOf(
                    "https://picsum.photos/600/400?random=10"
                )
            ),
            ScrapOrderItem(
                id = 402,
                name = "Thin Copper Wire",
                weight = 15,
                quantity = null,
                images = listOf(
                    "https://picsum.photos/600/400?random=11"
                )
            )
        )
    ),
    ScrapOrder(
        id = 5,
        title = "Mixed Glass",
        description = "Colored and transparent glass bottles.",
        location = "Mansoura",
        price = 15.0,
        date = "2025-09-01",
        userId = 104,
        items = listOf(
            ScrapOrderItem(
                id = 501,
                name = "Green Glass Bottles",
                weight = 6,
                quantity = 2,
                images = listOf(
                    "https://picsum.photos/600/400?random=12"
                )
            ),
            ScrapOrderItem(
                id = 502,
                name = "Transparent Glass Bottles",
                weight = 8,
                quantity = 4,
                images = listOf(
                    "https://picsum.photos/600/400?random=13"
                )
            )
        )
    ),
    ScrapOrder(
        id = 6,
        title = "Wooden Pallets",
        description = "Old wooden pallets from warehouses.",
        location = "Cairo - Shubra",
        price = 40.0,
        date = "2025-08-28",
        userId = 105,
        items = listOf(
            ScrapOrderItem(
                id = 601,
                name = "Pallets",
                weight = 12,
                quantity = 10,
                images = listOf(
                    "https://picsum.photos/600/400?random=14"
                )
            )
        )
    ),
    ScrapOrder(
        id = 7,
        title = "Old Electronics",
        description = "E-waste collection of computers and phones.",
        location = "Cairo - Downtown",
        price = 120.0,
        date = "2025-09-11",
        userId = 106,
        items = listOf(
            ScrapOrderItem(
                id = 701,
                name = "Old Laptops",
                weight = 4,
                quantity = 6,
                images = listOf(
                    "https://picsum.photos/600/400?random=15"
                )
            ),
            ScrapOrderItem(
                id = 702,
                name = "Mobile Phones",
                weight = 2,
                quantity = 10,
                images = listOf(
                    "https://picsum.photos/600/400?random=16"
                )
            )
        )
    ),
    ScrapOrder(
        id = 8,
        title = "Textile Waste",
        description = "Fabric scraps from tailoring shops.",
        location = "Cairo - Helwan",
        price = 10.0,
        date = "2025-09-03",
        userId = 107,
        items = listOf(
            ScrapOrderItem(
                id = 801,
                name = "Cotton Fabric",
                weight = 5,
                quantity = 7,
                images = listOf(
                    "https://picsum.photos/600/400?random=17"
                )
            ),
            ScrapOrderItem(
                id = 802,
                name = "Wool Fabric",
                weight = 3,
                quantity = 4,
                images = listOf(
                    "https://picsum.photos/600/400?random=18"
                )
            )
        )
    ),
    ScrapOrder(
        id = 9,
        title = "Plastic Crates",
        description = "Used crates from vegetable markets.",
        location = "Beni Suef",
        price = 18.0,
        date = "2025-09-08",
        userId = 108,
        items = listOf(
            ScrapOrderItem(
                id = 901,
                name = "Large Plastic Crates",
                weight = 9,
                quantity = 5,
                images = listOf(
                    "https://picsum.photos/600/400?random=19"
                )
            )
        )
    ),
    ScrapOrder(
        id = 10,
        title = "Mixed Metals",
        description = "Brass and steel mixed scrap.",
        location = "Cairo - Obour",
        price = 90.0,
        date = "2025-09-14",
        userId = 109,
        items = listOf(
            ScrapOrderItem(
                id = 1001,
                name = "Brass Scrap",
                weight = 7,
                quantity = 3,
                images = listOf(
                    "https://picsum.photos/600/400?random=20"
                )
            ),
            ScrapOrderItem(
                id = 1002,
                name = "Steel Scrap",
                weight = 15,
                quantity = 6,
                images = listOf(
                    "https://picsum.photos/600/400?random=21"
                )
            )
        )
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
    MarketScreen(
        onDetailsClick = {}
    )
}