package com.vfutia.lurk.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vfutia.lurk.ui.theme.LurkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationTopBar(
    title: String,
    allowBackNavigation: Boolean = true,
    showMenu: Boolean = false,
    actions: @Composable RowScope.() -> Unit = { },
    onBackClicked: () -> Unit = { },
    onMenuClicked: () -> Unit = { }
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            when {
                allowBackNavigation -> {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
                showMenu -> {
                    IconButton(onClick = onMenuClicked) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            }
        },
        actions = actions,
        title = {
            Text(title)
        }
    )
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Full", showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED)
@Composable
fun ApplicationTopBarPreview() {
    LurkTheme {
        Surface {
            ApplicationTopBar(title = "Top Bar Preview")
        }
    }
}

@Preview(name = "Light Mode No Back", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode No Back", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Full No Back", showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED)
@Composable
fun ApplicationTopBarNoBackPreview() {
    LurkTheme {
        Surface {
            ApplicationTopBar(title = "Top Bar Preview", allowBackNavigation = false)
        }
    }
}