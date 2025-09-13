package com.delighted2wins.souqelkhorda.core.extensions

import android.app.Activity
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

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