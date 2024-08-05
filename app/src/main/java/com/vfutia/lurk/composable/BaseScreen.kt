package com.vfutia.lurk.composable

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vfutia.lurk.ui.theme.LurkTheme
import kotlinx.coroutines.launch

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    title: String,
    allowBackNavigation: Boolean = true,
    showTopBar: Boolean = true,
    actions: @Composable RowScope.() -> Unit = { },
    drawerSheet: @Composable ColumnScope.() -> Unit = { },
    content: @Composable (snackbarHostState: SnackbarHostState) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LurkTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = { ModalDrawerSheet(content = drawerSheet) },
        ) {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    if (showTopBar) {
                        ApplicationTopBar(
                            title = title,
                            allowBackNavigation = allowBackNavigation,
                            showMenu = drawerSheet != { },
                            actions = actions,
                            onBackClicked = { onBackPressedDispatcher?.onBackPressed() },
                            onMenuClicked = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
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
}