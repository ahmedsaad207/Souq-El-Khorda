package com.delighted2wins.souqelkhorda.app.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LightColorScheme = lightColorScheme(
    primary = Til,
    onPrimary = Color.White,
    primaryContainer = Til,
    onPrimaryContainer = Color.White,

    secondary = LightFirstBottomNavColor,
    onSecondary = Color.White,
    secondaryContainer = LightSecondBottomNavColor,
    onSecondaryContainer = Color.White,

    background = LightBackground,
    onBackground = Color.Black,
    surface = LightBackground,
    onSurface = Color.Black,

    surfaceVariant = LightTextFieldBackground,
    onSurfaceVariant = LightTextHint,

    error = ErrorLight,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410001),

    outline = LightBorderUnfocused,

    inverseSurface = DarkBackground,
    inverseOnSurface = Color.White,
    tertiary = LightSubCard,

    surfaceContainer = LightCardGradientColor1,
    surfaceContainerLow = LightCardGradientColor2,
    surfaceTint = LightCardGradientColor3
)

val DarkColorScheme = darkColorScheme(
    primary = DarkTil,
    onPrimary = Color.White,
    primaryContainer = DarkTil,
    onPrimaryContainer = DarkTextFieldBackground,

    secondary = DarkFirstBottomNavColor,
    onSecondary = Color.Black,
    secondaryContainer = DarkSecondBottomNavColor,
    onSecondaryContainer = Color(0xFF223D39),

    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkTextFieldBackground,
    onSurface = Color.White,

    surfaceVariant = DarkTextFieldBackground,
    onSurfaceVariant = DarkTextHint,

    error = DarkError,
    onError = Color.Black,
    errorContainer = Color(0xFF8B1D2E),
    onErrorContainer = Color.White,

    outline = DarkBorderUnfocused,

    inverseSurface = LightBackground,
    inverseOnSurface = Color.Black,
    tertiary = DarkSubCard,
    surfaceContainer = DarkCardGradientColor1,
    surfaceContainerLow = DarkCardGradientColor2,
    surfaceTint = DarkCardGradientColor3
)

@Composable
fun SouqElKhordaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}