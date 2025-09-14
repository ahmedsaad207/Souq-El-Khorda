package com.delighted2wins.souqelkhorda.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.rememberNavBackStack
import com.delighted2wins.souqelkhorda.app.theme.SouqElKhordaTheme
import com.delighted2wins.souqelkhorda.core.components.AppBottomNavBar
import com.delighted2wins.souqelkhorda.core.components.CustomTopAppBar
import com.delighted2wins.souqelkhorda.core.extensions.configureSystemUI
import com.delighted2wins.souqelkhorda.navigation.NavigationRoot
import com.delighted2wins.souqelkhorda.navigation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var snackBarHostState: SnackbarHostState
    lateinit var bottomBarState: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            configureSystemUI(isSystemInDarkTheme())

            snackBarHostState = remember { SnackbarHostState() }
            bottomBarState = remember { mutableStateOf(false) }

            val backStack = rememberNavBackStack(SplashScreen)
            SouqElKhordaTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackBarHostState,
                            modifier = Modifier.padding(bottom = 50.dp)
                        )
                    },
                    topBar = {
                        if (bottomBarState.value) {
                            CustomTopAppBar(
                                pageTitle = "Page Title",
                                userName = "username"
                            ) {}
                        } else {
                            null
                        }
                    },
                    bottomBar = {
                        if (bottomBarState.value) {
                            AppBottomNavBar(backStack)
                        } else {
                            null
                        }
                    }
                ) { innerPadding ->
                    NavigationRoot(
                        modifier = Modifier
                            .fillMaxSize()
//                            .padding(innerPadding),
                        ,
                        bottomBarState = bottomBarState,
                        snackBarState = snackBarHostState,
                        backStack = backStack
                    )
                }
            }

        }
    }
}



