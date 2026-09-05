package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val GeometricBalanceColorScheme = lightColorScheme(
    primary = LightGeoPrimary,
    onPrimary = Color.White,
    primaryContainer = LightGeoPrimaryContainer,
    onPrimaryContainer = LightGeoOnPrimaryContainer,
    secondary = LightGeoSky,
    onSecondary = LightGeoOnSky,
    secondaryContainer = LightGeoSky,
    onSecondaryContainer = LightGeoOnSky,
    tertiary = LightGeoMint,
    onTertiary = LightGeoOnMint,
    background = LightGeoBackground,
    onBackground = LightGeoTextPrimary,
    surface = LightGeoCard,
    onSurface = LightGeoTextPrimary,
    surfaceVariant = LightGeoSurface,
    onSurfaceVariant = LightGeoTextSecondary,
    outline = LightGeoBorder,
    outlineVariant = Color(0xFFE7E0EC)
)

private val DarkGeometricBalanceColorScheme = darkColorScheme(
    primary = DarkGeoPrimary,
    onPrimary = Color(0xFF381E72),
    primaryContainer = DarkGeoPrimaryContainer,
    onPrimaryContainer = DarkGeoOnPrimaryContainer,
    secondary = DarkGeoSky,
    onSecondary = DarkGeoOnSky,
    secondaryContainer = Color(0xFF2B2831),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = DarkGeoMint,
    onTertiary = DarkGeoOnMint,
    background = DarkGeoBackground,
    onBackground = DarkGeoTextPrimary,
    surface = DarkGeoCard,
    onSurface = DarkGeoTextPrimary,
    surfaceVariant = DarkGeoSurface,
    onSurfaceVariant = DarkGeoTextSecondary,
    outline = DarkGeoBorder,
    outlineVariant = Color(0xFF49454F)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val palette = if (darkTheme) DarkPalette else LightPalette
    val colorScheme = if (darkTheme) DarkGeometricBalanceColorScheme else GeometricBalanceColorScheme

    CompositionLocalProvider(LocalGeoColors provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
