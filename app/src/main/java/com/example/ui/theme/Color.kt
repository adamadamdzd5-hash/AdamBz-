package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

// Light Palette Definitions
val LightGeoBackground = Color(0xFFFEF7FF)
val LightGeoSurface = Color(0xFFF3EDF7)
val LightGeoCard = Color(0xFFFFFFFF)
val LightGeoCardAccent = Color(0xFFEADDFF)
val LightGeoBorder = Color(0xFFCAC4D0)

val LightGeoPrimary = Color(0xFF6750A4)
val LightGeoPrimaryContainer = Color(0xFFEADDFF)
val LightGeoOnPrimaryContainer = Color(0xFF21005D)

val LightGeoTextPrimary = Color(0xFF1D1B20)
val LightGeoTextSecondary = Color(0xFF49454F)
val LightGeoTextMuted = Color(0xFF79747E)

val LightGeoCoral = Color(0xFFFFDAD6)
val LightGeoOnCoral = Color(0xFF410002)
val LightGeoSky = Color(0xFFD3E3FD)
val LightGeoOnSky = Color(0xFF041E49)
val LightGeoMint = Color(0xFFC4EED0)
val LightGeoOnMint = Color(0xFF073812)
val LightGeoAmber = Color(0xFFFFDEA7)
val LightGeoOnAmber = Color(0xFF291800)

// Dark Palette Definitions (OLED / Deep Material 3 tones)
val DarkGeoBackground = Color(0xFF121116)
val DarkGeoSurface = Color(0xFF1D1B22)
val DarkGeoCard = Color(0xFF25232C)
val DarkGeoCardAccent = Color(0xFF3B3549)
val DarkGeoBorder = Color(0xFF49454F)

val DarkGeoPrimary = Color(0xFFD0BCFF)
val DarkGeoPrimaryContainer = Color(0xFF4F378B)
val DarkGeoOnPrimaryContainer = Color(0xFFEADDFF)

val DarkGeoTextPrimary = Color(0xFFE6E0E9)
val DarkGeoTextSecondary = Color(0xFFCAC4D0)
val DarkGeoTextMuted = Color(0xFF938F99)

val DarkGeoCoral = Color(0xFF5E1B1B)
val DarkGeoOnCoral = Color(0xFFFFDAD6)
val DarkGeoSky = Color(0xFF1E3A5F)
val DarkGeoOnSky = Color(0xFFD3E3FD)
val DarkGeoMint = Color(0xFF174226)
val DarkGeoOnMint = Color(0xFFC4EED0)
val DarkGeoAmber = Color(0xFF4D3300)
val DarkGeoOnAmber = Color(0xFFFFDEA7)

// Color Palette Data Structure
data class GeoColorPalette(
    val background: Color,
    val surface: Color,
    val card: Color,
    val cardAccent: Color,
    val border: Color,
    val primary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val coral: Color,
    val onCoral: Color,
    val sky: Color,
    val onSky: Color,
    val mint: Color,
    val onMint: Color,
    val amber: Color,
    val onAmber: Color,
    val isDark: Boolean
)

val LightPalette = GeoColorPalette(
    background = LightGeoBackground,
    surface = LightGeoSurface,
    card = LightGeoCard,
    cardAccent = LightGeoCardAccent,
    border = LightGeoBorder,
    primary = LightGeoPrimary,
    primaryContainer = LightGeoPrimaryContainer,
    onPrimaryContainer = LightGeoOnPrimaryContainer,
    textPrimary = LightGeoTextPrimary,
    textSecondary = LightGeoTextSecondary,
    textMuted = LightGeoTextMuted,
    coral = LightGeoCoral,
    onCoral = LightGeoOnCoral,
    sky = LightGeoSky,
    onSky = LightGeoOnSky,
    mint = LightGeoMint,
    onMint = LightGeoOnMint,
    amber = LightGeoAmber,
    onAmber = LightGeoOnAmber,
    isDark = false
)

val DarkPalette = GeoColorPalette(
    background = DarkGeoBackground,
    surface = DarkGeoSurface,
    card = DarkGeoCard,
    cardAccent = DarkGeoCardAccent,
    border = DarkGeoBorder,
    primary = DarkGeoPrimary,
    primaryContainer = DarkGeoPrimaryContainer,
    onPrimaryContainer = DarkGeoOnPrimaryContainer,
    textPrimary = DarkGeoTextPrimary,
    textSecondary = DarkGeoTextSecondary,
    textMuted = DarkGeoTextMuted,
    coral = DarkGeoCoral,
    onCoral = DarkGeoOnCoral,
    sky = DarkGeoSky,
    onSky = DarkGeoOnSky,
    mint = DarkGeoMint,
    onMint = DarkGeoOnMint,
    amber = DarkGeoAmber,
    onAmber = DarkGeoOnAmber,
    isDark = true
)

val LocalGeoColors = compositionLocalOf { LightPalette }

// Dynamic Composable Color Accessors
val GeoBackground: Color @Composable get() = LocalGeoColors.current.background
val GeoSurface: Color @Composable get() = LocalGeoColors.current.surface
val GeoCard: Color @Composable get() = LocalGeoColors.current.card
val GeoCardAccent: Color @Composable get() = LocalGeoColors.current.cardAccent
val GeoBorder: Color @Composable get() = LocalGeoColors.current.border

val GeoPrimary: Color @Composable get() = LocalGeoColors.current.primary
val GeoPrimaryContainer: Color @Composable get() = LocalGeoColors.current.primaryContainer
val GeoOnPrimaryContainer: Color @Composable get() = LocalGeoColors.current.onPrimaryContainer

val GeoTextPrimary: Color @Composable get() = LocalGeoColors.current.textPrimary
val GeoTextSecondary: Color @Composable get() = LocalGeoColors.current.textSecondary
val GeoTextMuted: Color @Composable get() = LocalGeoColors.current.textMuted

val GeoCoral: Color @Composable get() = LocalGeoColors.current.coral
val GeoOnCoral: Color @Composable get() = LocalGeoColors.current.onCoral
val GeoSky: Color @Composable get() = LocalGeoColors.current.sky
val GeoOnSky: Color @Composable get() = LocalGeoColors.current.onSky
val GeoMint: Color @Composable get() = LocalGeoColors.current.mint
val GeoOnMint: Color @Composable get() = LocalGeoColors.current.onMint
val GeoAmber: Color @Composable get() = LocalGeoColors.current.amber
val GeoOnAmber: Color @Composable get() = LocalGeoColors.current.onAmber

// Semantic Aliases
val TechDarkBg: Color @Composable get() = GeoBackground
val TechCardBg: Color @Composable get() = GeoCard
val TechCardVariant: Color @Composable get() = GeoSurface
val TechBorder: Color @Composable get() = GeoBorder
val TechCyan: Color @Composable get() = GeoPrimary
val TechBlue: Color @Composable get() = if (LocalGeoColors.current.isDark) Color(0xFF64B5F6) else Color(0xFF386A90)
val TechPurple: Color @Composable get() = GeoPrimary
val TechEmerald: Color @Composable get() = if (LocalGeoColors.current.isDark) Color(0xFF81C784) else Color(0xFF2E6B4F)
val TechAmber: Color @Composable get() = if (LocalGeoColors.current.isDark) Color(0xFFFFB74D) else Color(0xFF8C5000)
val TechRose: Color @Composable get() = if (LocalGeoColors.current.isDark) Color(0xFFE57373) else Color(0xFFBA1A1A)
val TechTextPrimary: Color @Composable get() = GeoTextPrimary
val TechTextSecondary: Color @Composable get() = GeoTextSecondary
val TechTextMuted: Color @Composable get() = GeoTextMuted

val LightBg: Color @Composable get() = GeoBackground
val LightCard: Color @Composable get() = GeoCard
val LightBorder: Color @Composable get() = GeoBorder
