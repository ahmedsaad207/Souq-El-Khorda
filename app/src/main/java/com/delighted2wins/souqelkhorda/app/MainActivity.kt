package com.delighted2wins.souqelkhorda.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.delighted2wins.souqelkhorda.core.components.CustomBottomNavBar
import com.delighted2wins.souqelkhorda.core.components.CustomTopAppBar
import com.delighted2wins.souqelkhorda.navigation.Routes
import com.delighted2wins.souqelkhorda.navigation.SetupNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var selectedRoute by remember { mutableStateOf(Routes.Direct_Sale) }
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        pageTitle = "Page Title",
                        userName = "username"
                    ) {}
                },
                bottomBar = {
                    CustomBottomNavBar(selectedRoute, navController)
                }
            ) { innerPadding ->

                SetupNavHost(
                    navController = navController
                )
            }
        }
    }

}

