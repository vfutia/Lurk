package com.vfutia.lurk.composable

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vfutia.lurk.ui.theme.LurkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationTopBar(title: String, allowBackNavigation: Boolean = true) {
    LurkTheme {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            navigationIcon = {
                if (allowBackNavigation) {
                    IconButton(onClick = {  }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            },
            title = {
                Text(title)
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ApplicationTopBarPreview() {
    ApplicationTopBar(title = "Top Bar Preview")
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ApplicationTopBarDarkPreview() {
    ApplicationTopBar(title = "Top Bar Preview")
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ApplicationTopBarNoBackNavPreview() {
    ApplicationTopBar(title = "Top Bar Preview", allowBackNavigation = false)
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ApplicationTopBarNoBackNavDarkPreview() {
    ApplicationTopBar(title = "Top Bar Preview", allowBackNavigation = false)
}