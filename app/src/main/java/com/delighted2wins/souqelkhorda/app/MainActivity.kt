package com.delighted2wins.souqelkhorda.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import com.delighted2wins.souqelkhorda.core.components.AppBottomNavBar
import com.delighted2wins.souqelkhorda.core.components.CustomTopAppBar
import com.delighted2wins.souqelkhorda.navigation.NavigationRoot
import com.delighted2wins.souqelkhorda.navigation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = rememberNavBackStack(SplashScreen)
            val isSplashScreen = remember { mutableStateOf(true) }
            Scaffold(
                topBar = {
                    if (!isSplashScreen.value) {
                        CustomTopAppBar(
                            pageTitle = "Page Title",
                            userName = "username"
                        ) {}
                    } else {
                        null
                    }
                },
                bottomBar = {
                    if (!isSplashScreen.value) {
                        AppBottomNavBar(backStack)
                    } else {
                        null
                    }
                }
            ) { innerPadding ->
                NavigationRoot(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    isSplashScreen = isSplashScreen,
                    backStack = backStack
                )
            }
        }
    }
}