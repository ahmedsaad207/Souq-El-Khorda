package com.delighted2wins.souqelkhorda.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
)

val bottomNavigationItemsList = listOf(
    BottomNavigationItem(
        title = "Direct Sale",
        selectedIcon = Icons.Filled.Build,
        unSelectedIcon = Icons.Outlined.Build
    ),
    BottomNavigationItem(
        title = "Market",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings
    ),
    BottomNavigationItem(
        title = "Nearest Buyers",
        selectedIcon = Icons.Filled.ThumbUp,
        unSelectedIcon = Icons.Outlined.ThumbUp
    )
)