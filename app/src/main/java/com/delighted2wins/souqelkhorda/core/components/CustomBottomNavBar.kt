package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.delighted2wins.souqelkhorda.navigation.bottomNavItem

@Composable
fun CustomBottomNavBar(
    selectedRoute: String,
    navController: NavHostController
) {
    var selectedRoute1 = selectedRoute
    NavigationBar {
        bottomNavItem.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute1 == item.route,
                onClick = {
                    selectedRoute1 = item.route
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {},
                label = { Text(item.label) }
            )
        }
    }
}