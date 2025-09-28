package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.delighted2wins.souqelkhorda.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppTopAppBar(
    userName: String = "Abdelaziz",
    screenName: String = "Home",
    userImage: String? = null,
    notificationCount: Int = 3,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    onProfileClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme

    val gradientBrush = Brush.horizontalGradient(
        listOf(colorScheme.secondary, colorScheme.secondaryContainer)
    )

    val waveShape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(0f, size.height - 40f)
        quadraticBezierTo(
            size.width / 2, size.height + 40f,
            size.width, size.height - 40f
        )
        lineTo(size.width, 0f)
        close()
    }

    TopAppBar(
        title = {
            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = screenName,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = userName,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        actions = {
            IconButton(onClick = onNotificationClick) {
                BadgedBox(
                    badge = {
                        if (notificationCount > 0) {
                            Badge {
                                Text(
                                    text = notificationCount.toString(),
                                    color = Color.White
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notifications",
                        tint = colorScheme.background
                    )
                }
            }
            IconButton(onClick = onProfileClick) {
                AsyncImage(
                    model = userImage,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.avatar),
                    error = painterResource(R.drawable.avatar),
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier.background(gradientBrush, shape = waveShape),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
    )
}