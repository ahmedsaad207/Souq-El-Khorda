package com.delighted2wins.souqelkhorda.app

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation3.runtime.rememberNavBackStack
import com.delighted2wins.souqelkhorda.core.components.AppBottomNavBar
import com.delighted2wins.souqelkhorda.core.components.CustomTopAppBar
import com.delighted2wins.souqelkhorda.navigation.NavigationRoot
import com.delighted2wins.souqelkhorda.navigation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var snackBarHostState: SnackbarHostState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            snackBarHostState = remember { SnackbarHostState() }
            val isSplashScreen = remember { mutableStateOf(false) }
            var selectedItem by rememberSaveable {
                mutableIntStateOf(0)
            }
            configureSystemUI(isSystemInDarkTheme())

            val backStack = rememberNavBackStack(SplashScreen)
            val isSplashScreen = remember { mutableStateOf(true) }
            //HideSystemUI()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackBarHostState,
                        modifier = Modifier.padding(bottom = 50.dp)
                    )
                },
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
                    isSplashScreen = isSplashScreen,
                    snackBarState = snackBarHostState,

                    )
            }
        }
    }
}

fun Activity.configureSystemUI(darkTheme: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val controller = WindowInsetsControllerCompat(window, window.decorView)

    window.statusBarColor = if (darkTheme) 0xFF121212.toInt() else 0xFFFFFFFF.toInt()
    controller.isAppearanceLightStatusBars = !darkTheme

    window.navigationBarColor = Color.Transparent.value.toInt()
    controller.isAppearanceLightNavigationBars = !darkTheme

    controller.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    controller.hide(WindowInsetsCompat.Type.navigationBars())
    controller.show(WindowInsetsCompat.Type.statusBars())
}

