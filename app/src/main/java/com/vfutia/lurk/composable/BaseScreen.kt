package com.vfutia.lurk.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.vfutia.lurk.ui.theme.LurkTheme

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    title: String,
    allowBackNavigation: Boolean = true,
    showTopBar: Boolean = true,
    content: @Composable (snackbarHostState: SnackbarHostState) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LurkTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                if (showTopBar) {
                    ApplicationTopBar(
                        title = title,
                        allowBackNavigation = allowBackNavigation
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                content(snackbarHostState)
            }
        }
    }
}