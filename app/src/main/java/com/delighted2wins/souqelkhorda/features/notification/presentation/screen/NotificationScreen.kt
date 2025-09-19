package com.delighted2wins.souqelkhorda.features.notification.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.features.notification.presentation.components.NotificationCard


@Composable
fun NotificationsScreen(
    notifications: List<NotificationItem>,
    onBackClick: () -> Unit = {},
    onDismiss: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        OneIconCard(
            onClick = onBackClick,
            headerTxt = "Notifications",
            titleSize = 22
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(notifications, key = { it.id }) { item ->
                NotificationCard(
                    imageUrl = "",
                    title = item.title,
                    description = item.description,
                    tag = item.tag,
                    tagColor = when (item.type) {
                        NotificationType.SELLING -> Color(0xFF00B259)
                        NotificationType.BUYING -> Color(0xFF2A62FF)
                    },
                    time = item.time,
                    unread = item.unread,
                    onDismiss = { onDismiss(item.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    val dummyNotifications = listOf(
        NotificationItem(
            id = 1,
            title = "New Offer Received",
            description = "Someone is interested in your old furniture set. Check the offer details and respond.",
            tag = "SELLING",
            time = "2 hours ago",
            type = NotificationType.SELLING,
            unread = true
        ),
        NotificationItem(
            id = 2,
            title = "New Offer Received",
            description = "A bicycle matching your search criteria is now available nearby. Act fast!",
            tag = "BUYING",
            time = "5 hours ago",
            type = NotificationType.BUYING,
            unread = false
        )
    )

    NotificationsScreen(
        notifications = dummyNotifications,
        onDismiss = {  },
    )
}


data class NotificationItem(
    val id: Int,
    val title: String,
    val description: String,
    val tag: String,
    val time: String,
    val type: NotificationType,
    val unread: Boolean = true
)

enum class NotificationType {
    SELLING,
    BUYING
}