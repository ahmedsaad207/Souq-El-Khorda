package com.delighted2wins.souqelkhorda.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.CustomTopAppBar
import com.delighted2wins.souqelkhorda.navigation.NavigationRoot
import com.delighted2wins.souqelkhorda.navigation.bottomNavigationItemsList
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
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackBarHostState,
                        modifier = Modifier.padding(bottom = 50.dp)
                    )
                },
                topBar = {
                    if (isSplashScreen.value) {
                        CustomTopAppBar(
                            pageTitle = "Page Title",
                            userName = "username"
                        ) {}
                    } else {
                        null
                    }
                },
                bottomBar = {
                    if (isSplashScreen.value) {
                        NavigationBar {
                            bottomNavigationItemsList.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItem == index,
                                    onClick = {
                                        selectedItem = index
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItem) {
                                                item.selectedIcon
                                            } else item.unSelectedIcon,
                                            contentDescription = item.title
                                        )
                                    },
                                )
                            }
                        }
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
                    snackBarState = snackBarHostState,

                    )
            }
        }
    }

}

