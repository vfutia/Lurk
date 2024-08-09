package com.vfutia.lurk

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.graphics.toArgb
import com.vfutia.lurk.ui.theme.LurkTheme

open class BaseActivity : ComponentActivity()

fun BaseActivity.setContentAndStatusBar(
    parent: CompositionContext? = null,
    content: @Composable () -> Unit
) {
    setContent(parent) {
        LurkTheme {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(MaterialTheme.colorScheme.primary.toArgb()),
                navigationBarStyle = SystemBarStyle.dark(MaterialTheme.colorScheme.primary.toArgb())
            )

            content()
        }
    }
}