package com.delighted2wins.souqelkhorda.features.notification.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.EmptyCart
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.core.extensions.toRelativeTime
import com.delighted2wins.souqelkhorda.features.notification.presentation.components.NotificationCard
import com.delighted2wins.souqelkhorda.features.notification.presentation.components.NotificationCardShimmer
import com.delighted2wins.souqelkhorda.features.notification.presentation.contract.NotificationsContract
import com.delighted2wins.souqelkhorda.features.notification.presentation.viewmodel.NotificationsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        OneIconCard(
            modifier = Modifier
                .statusBarsPadding()
                .padding(8.dp),
            onClick = onBackClick,
            headerTxt = stringResource(R.string.notifications),
            titleSize = 22
        )

        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.handleIntent(NotificationsContract.Intent.Refresh) },
            modifier = Modifier.fillMaxSize(),
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = pullToRefreshState,
                    isRefreshing = state.isLoading,
                    containerColor = MaterialTheme.colorScheme.surface,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    state.isLoading -> {
                        items(6) {
                            Column {
                                NotificationCardShimmer()
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                                )
                            }
                        }
                    }

                    state.error != null -> {
                        item {

                        }
                    }

                    else -> {
                        items(state.notifications, key = { it.id }) { item ->
                            when (state.notifications.isEmpty()) {
                                true -> {
                                    EmptyCart(R.raw.no_data,
                                        context.getString(R.string.nothing_to_see_here_yet))
                                }
                                false -> {
                                    Column {
                                        NotificationCard(
                                            imageUrl = item.imageUrl,
                                            title = item.title,
                                            description = item.message,
                                            time = item.createdAt.toRelativeTime(context),
                                            unread = item.read.not(),
                                            onDismiss = {
                                                viewModel.handleIntent(
                                                    NotificationsContract.Intent.Dismiss(
                                                        item.id
                                                    )
                                                )
                                            },
                                            onItemClick = {
                                                viewModel.handleIntent(
                                                    NotificationsContract.Intent.MarkAsRead(
                                                        item.id
                                                    )
                                                )
                                                onItemClick(item.id)
                                            }
                                        )
                                    }
                                    HorizontalDivider(
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

