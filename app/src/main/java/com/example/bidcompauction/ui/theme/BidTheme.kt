package com.example.bidcompauction.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFC107),
    onPrimary = Color(0xFF111111),
    background = Color(0xFF0B0B0B),
    surface = Color(0xFF121212),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun BidTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
