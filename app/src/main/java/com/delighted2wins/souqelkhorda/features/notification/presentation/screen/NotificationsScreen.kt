package com.delighted2wins.souqelkhorda.features.notification.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.core.extensions.toRelativeTime
import com.delighted2wins.souqelkhorda.features.notification.presentation.components.NotificationCard
import com.delighted2wins.souqelkhorda.features.notification.presentation.viewmodel.NotificationsViewModel
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.Alignment
import com.delighted2wins.souqelkhorda.features.notification.presentation.components.NotificationCardShimmer
import com.delighted2wins.souqelkhorda.features.notification.presentation.contract.NotificationsContract


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()

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
                        items(3) {
                            Column {
                                NotificationCardShimmer()
                                Divider(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                    thickness = 1.dp
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
                            Column {
                                NotificationCard(
                                    imageUrl = item.imageUrl,
                                    title = item.title,
                                    description = item.message,
                                    time = item.createdAt.toRelativeTime(),
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
                            Divider(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

