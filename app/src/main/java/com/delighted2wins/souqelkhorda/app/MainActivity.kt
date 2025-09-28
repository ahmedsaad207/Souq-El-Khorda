package com.delighted2wins.souqelkhorda.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.rememberNavBackStack
import com.delighted2wins.souqelkhorda.app.theme.SouqElKhordaTheme
import com.delighted2wins.souqelkhorda.core.components.AppBottomNavBar
import com.delighted2wins.souqelkhorda.core.components.AppTopAppBar
import com.delighted2wins.souqelkhorda.core.enums.LanguageEnum
import com.delighted2wins.souqelkhorda.core.extensions.applyLanguage
import com.delighted2wins.souqelkhorda.core.extensions.configureSystemUI
import com.delighted2wins.souqelkhorda.core.extensions.requestNotificationPermission
import com.delighted2wins.souqelkhorda.features.profile.di.MainActivityEntryPoint
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.GetLanguageUseCase
import com.delighted2wins.souqelkhorda.navigation.NavigationRoot
import com.delighted2wins.souqelkhorda.navigation.NotificationsScreen
import com.delighted2wins.souqelkhorda.navigation.ProfileScreen
import com.delighted2wins.souqelkhorda.navigation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    lateinit var snackBarHostState: SnackbarHostState
    lateinit var bottomBarState: MutableState<Boolean>
    lateinit var navState: MutableState<Boolean>
    lateinit var screenNameState: MutableState<String>

    @Inject
    lateinit var getLanguageUseCase: GetLanguageUseCase

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "MainActivity created, VM instance = $mainViewModel")
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            configureSystemUI(isSystemInDarkTheme())

            snackBarHostState = remember { SnackbarHostState() }
            bottomBarState = remember { mutableStateOf(false) }
            navState = remember { mutableStateOf(true) }
            screenNameState = remember { mutableStateOf("") }

            val backStack = rememberNavBackStack(SplashScreen)
            SouqElKhordaTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                val state by mainViewModel.state.collectAsStateWithLifecycle()
                val user by mainViewModel.user.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = if (navState.value) Modifier else Modifier.nestedScroll(
                        scrollBehavior.nestedScrollConnection
                    ),
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackBarHostState,
                            modifier = Modifier.padding(bottom = 50.dp)
                        )
                    },
                    topBar = {
                        if (bottomBarState.value) {
                            AppTopAppBar(
                                userImage = user?.imageUrl,
                                userName = user?.name ?: "Hello User",
                                notificationCount = state.notificationCount,
                                scrollBehavior = scrollBehavior,
                                screenName = screenNameState.value,
                                onProfileClick = { backStack.add(ProfileScreen) },
                                onNotificationClick = { backStack.add(NotificationsScreen) }
                            )
                        }
                    },
                    bottomBar = {
                        if (bottomBarState.value) {
                            AppBottomNavBar(backStack)
                        }
                    }
                ) { innerPadding ->
                    NavigationRoot(
                        modifier = Modifier
                            .fillMaxWidth(),
                        bottomBarState = bottomBarState,
                        snackBarState = snackBarHostState,
                        backStack = backStack,
                        innerPadding = innerPadding,
                        navState = navState,
                        screenNameState = screenNameState
                    )
                }
            }

        }
    }

    override fun attachBaseContext(newBase: Context) {
        val entryPoint = EntryPointAccessors.fromApplication(
            newBase.applicationContext,
            MainActivityEntryPoint::class.java
        )

        val lang = entryPoint.getLanguageUseCase()

        val languageCode = if (lang() == LanguageEnum.DEFAULT.code) {
            newBase.resources.configuration.locales[0].language
        } else {
            lang()
        }

        val context = newBase.applyLanguage(languageCode)
        super.attachBaseContext(context)
    }

}



