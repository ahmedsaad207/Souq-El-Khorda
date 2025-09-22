package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.features.myorders.presentation.contract.MyOrdersIntents
import com.delighted2wins.souqelkhorda.features.myorders.presentation.viewmodel.MyOrdersViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersScreen(
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: MyOrdersViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val systemIsRtl = LocalConfiguration.current.layoutDirection == LayoutDirection.Rtl.ordinal
    val tabs = listOf(
        OrderSource.COMPANY to state.saleCount,
        OrderSource.MARKET to (state.offersCount + state.sellsCount)
    )

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> viewModel.onIntent(MyOrdersIntents.LoadSaleOrders)
            1 -> viewModel.onIntent(MyOrdersIntents.LoadSells)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(innerPadding)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            indicator = {}
        ) {
            tabs.forEachIndexed { index, (title, count) ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = isSelected,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    else Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = title.name,
                                fontSize = 20.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                            )

                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text(
                                    text = count.toString(),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> CompanyOrdersScreen(
                    state.saleOrders,
                    state.isLoading,
                    state.error,
                    systemIsRtl
                )
                1 -> MarketOrdersScreen(
                    state = state,
                    onChipSelected = { chip ->
                        when (chip) {
                            "Sells" -> viewModel.onIntent(MyOrdersIntents.LoadSells)
                            "Offers" -> viewModel.onIntent(MyOrdersIntents.LoadOffers)
                        }
                    },
                    systemIsRtl
                )
            }
        }
    }
}
