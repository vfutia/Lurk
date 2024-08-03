package com.vfutia.lurk.subreddit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vfutia.lurk.R
import com.vfutia.lurk.composable.BaseScreen
import com.vfutia.lurk.composable.MinimalPostContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubredditActivity : ComponentActivity() {
    companion object {
        const val KEY_SUBREDDIT = "subreddit"

        fun launchIntent(context: Context, subreddit: String = "news"): Intent {
            return Intent(context, SubredditActivity::class.java).apply {
                putExtra(KEY_SUBREDDIT, subreddit)
            }
        }
    }

    private val viewModel: SubredditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.fetchPage(intent.extras?.getString(KEY_SUBREDDIT) ?: "news")

        setContent {
            BaseScreen(title = getString(R.string.app_name), allowBackNavigation = false) {
                val state: SubredditState by viewModel.state.collectAsState()

                LazyColumn {
                    state.posts.forEach() { post ->
                        item {
                            MinimalPostContainer(post = post)
                        }
                    }
                }
            }
        }
    }
}
