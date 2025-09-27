package com.delighted2wins.souqelkhorda.features.notification.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.core.extensions.toRelativeTime
import com.delighted2wins.souqelkhorda.features.notification.presentation.components.NotificationCard
import com.delighted2wins.souqelkhorda.features.notification.presentation.viewmodel.NotificationsViewModel


@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        OneIconCard(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onBackClick,
            headerTxt = "Notifications",
            titleSize = 22
        )

        Box(
            modifier = Modifier
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    state.isLoading -> {

                    }

                    state.error != null -> {

                    }

                    else -> {
                        items(state.notifications, key = { it.id }) { item ->
                            NotificationCard(
                                imageUrl = "",
                                title = item.title,
                                description = item.message,
                                time = item.createdAt.toRelativeTime(),
                                unread = item.read,
                                onDismiss = { },
                                onItemClick = { onItemClick(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
