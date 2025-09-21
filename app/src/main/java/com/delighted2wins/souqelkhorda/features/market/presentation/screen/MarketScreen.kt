package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.delighted2wins.souqelkhorda.app.theme.Til
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ScrapCard
import com.delighted2wins.souqelkhorda.features.market.presentation.component.ScrapCardShimmer
import com.delighted2wins.souqelkhorda.features.market.presentation.component.SearchBar
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketEffect
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketIntent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    innerPadding: PaddingValues = PaddingValues(),
    snackBarHostState: SnackbarHostState,
    viewModel: MarketViewModel = hiltViewModel(),
    navigateToMakeOffer: () -> Unit = {},
    onDetailsClick: (Order) -> Unit,
    navToAddItem: () -> Unit = {}
) {
    val state = viewModel.state
    val isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
    val pullToRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MarketEffect.NavigateToOrderDetails -> onDetailsClick(effect.order)
                is MarketEffect.ShowError -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = effect.message,
                            actionLabel = if (isRtl) "إعادة المحاولة" else "Retry"
                        ).let { result ->
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onIntent(MarketIntent.Refresh)
                            }
                        }
                    }
                }
                is MarketEffect.NavigateToSellNow -> navToAddItem()
            }
        }
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = state.isRefreshing,
        onRefresh = { viewModel.onIntent(MarketIntent.Refresh) },
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        when {
            state.isLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(10) { ScrapCardShimmer(systemIsRtl = isRtl) }
                }
            }

            state.error != null -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.error,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            state.isEmpty -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isRtl) "لا توجد عروض متاحة الآن" else "No Offers Available Now",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.LightGray
                            )
                        }
                    }
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
                            onQueryChange = {
                                viewModel.onIntent(MarketIntent.SearchQueryChanged(it))
                            },
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

                        var user by remember { mutableStateOf<MarketUser?>(null) }

                        LaunchedEffect(scrapData) {
                            viewModel.getUserData(scrapData.userId) { loadedUser ->
                                user = loadedUser
                            }
                        }

                        user?.let { loadedUser ->
                            ScrapCard(
                                marketUser = loadedUser,
                                scrap = scrapData,
                                onBuyClick = { navigateToMakeOffer() },
                                onDetailsClick = { onDetailsClick(scrapData) },
                                systemIsRtl = isRtl
                            )
                        } ?: ScrapCardShimmer(systemIsRtl = isRtl)
                    }

                    item { Spacer(modifier = Modifier.padding(60.dp)) }
                }
            }
        }
    }
}
