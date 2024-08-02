package com.vfutia.lurk.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun SplashTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            background = MyrtleGreen,
            primary = White,
            onPrimary = White,
            onPrimaryContainer = White
        ),
        typography = Typography,
        content = content
    )
}