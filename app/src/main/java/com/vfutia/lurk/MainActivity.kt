package com.vfutia.lurk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import com.vfutia.lurk.composable.BaseScreen
import com.vfutia.lurk.composable.MinimalPostContainer
import com.vfutia.lurk.model.Post
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        fun launchIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaseScreen(title = getString(R.string.app_name), allowBackNavigation = false) {
                LazyColumn {
                    for (i in 0..3) {
                        item {
                            MinimalPostContainer(post = Post(i.toString(), "asdoifjoiasdjfoi"))
                        }
                    }
                }
            }
        }
    }
}
