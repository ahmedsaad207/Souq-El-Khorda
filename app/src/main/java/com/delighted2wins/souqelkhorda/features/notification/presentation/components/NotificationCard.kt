package com.delighted2wins.souqelkhorda.features.notification.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.app.theme.AppTypography

@Preview(showBackground = true)
@Composable
fun NotificationCard(
    imageUrl: String? = null,
    title: String = "New Offer Received",
    description: String = "Someone is interested in your old furniture set. Check the offer details and respond.",
    time: String = "2 hours ago",
    unread: Boolean = true,
    onDismiss: () -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    val backgroundColor = if (unread) {
        if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
        }
    } else {
        if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            MaterialTheme.colorScheme.surface
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onItemClick() },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.avatar),
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = description,
                        style = AppTypography.bodySmall.copy(fontWeight = FontWeight.Normal),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (!unread) {
                        Text(
                            text = stringResource(R.string.read),
                            style = AppTypography.labelSmall.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = time,
                    style = AppTypography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = colors.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}



