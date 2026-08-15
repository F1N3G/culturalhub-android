package com.g.culturalhub.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Burgundy500,
    onPrimary = Color.White,
    primaryContainer = Burgundy100,
    onPrimaryContainer = Burgundy900,
    secondary = Magenta500,
    onSecondary = Color.White,
    secondaryContainer = Magenta100,
    onSecondaryContainer = Burgundy900,
    tertiary = Copper400,
    onTertiary = Color.White,
    tertiaryContainer = Copper100,
    onTertiaryContainer = Copper700,
    background = Cream50,
    onBackground = Ink700,
    surface = Cream100,
    onSurface = Ink700,
    surfaceVariant = Cream100,
    onSurfaceVariant = Ink500,
)

private val DarkColors = darkColorScheme(
    primary = Magenta500,            // accent magenta, ca web-ul
    onPrimary = Color.White,
    primaryContainer = Burgundy600,
    onPrimaryContainer = Burgundy100,
    secondary = Magenta400,
    tertiary = Copper400,
    background = Ink900,
    onBackground = Cream50,
    surface = Ink800,
    onSurface = Cream50,
    surfaceVariant = Ink700,
    onSurfaceVariant = Color(0xFF9AA3B2),
)

// dynamicColor scos intenționat: vrem culorile brandului, nu cele din wallpaper.
// darkTheme = true forțat: pagina de evenimente din web e dark. (Comutatorul îl adăugăm mai târziu.)
@Composable
fun CulturalHubTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}