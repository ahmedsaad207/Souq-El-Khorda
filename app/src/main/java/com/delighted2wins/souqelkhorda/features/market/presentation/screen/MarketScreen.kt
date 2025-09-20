package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.delighted2wins.souqelkhorda.features.market.domain.entities.User
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ScrapCard
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ShimmerScrapCard
import com.delighted2wins.souqelkhorda.features.market.presentation.component.SearchBar
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketEffect
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketIntent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

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
                is MarketEffect.NavigateToOrderDetails -> { onDetailsClick(effect.order) }
                is MarketEffect.ShowError -> { /* show snackbar, etc. */ }
                is MarketEffect.NavigateToSellNow -> { navToAddItem() }
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isRefreshing),
        onRefresh = { viewModel.onIntent(MarketIntent.Refresh) },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                scale = true,
            )
        },
        modifier = Modifier.padding(innerPadding)
    ) {
        when {
            state.isLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(10) { ShimmerScrapCard(systemIsRtl = isRtl) }
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isRtl) "حدث خطأ" else "Something went wrong",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SearchBar(
                            query = state.query,
                            onQueryChange = { viewModel.onIntent(MarketIntent.SearchQueryChanged(it)) },
                            modifier = Modifier.fillMaxWidth(),
                            isRtl = isRtl
                        )
                    }

                    item {
                        CompositionLocalProvider(
                            LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
                        ) {
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
                            }
                        }
                    }

                    items(
                        state.successfulOrders.filter {
                            it.title.contains(state.query, ignoreCase = true) || state.query.isBlank()
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

                    item { Spacer(modifier = Modifier.padding(60.dp)) }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MarketScreenPreview() {
    MarketScreen(
        onDetailsClick = {}
    )
}