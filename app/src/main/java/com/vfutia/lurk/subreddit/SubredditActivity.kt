package com.vfutia.lurk.subreddit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import com.vfutia.lurk.R
import com.vfutia.lurk.composable.BaseScreen
import com.vfutia.lurk.composable.Loader
import com.vfutia.lurk.composable.MinimalPostContainer
import com.vfutia.lurk.composable.SubredditBanner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubredditActivity : ComponentActivity() {
    companion object {
        const val KEY_SUBREDDIT = "subreddit"

        fun launchIntent(context: Context, subreddit: String? = null): Intent {
            return Intent(context, SubredditActivity::class.java).apply {
                putExtra(KEY_SUBREDDIT, subreddit)
            }
        }
    }

    private val viewModel: SubredditViewModel by viewModels()
    private var subreddit: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.apply {
            subreddit = intent.extras?.getString(KEY_SUBREDDIT)

            fetchPage(subreddit)

            subreddit?.let { fetchSubreddit(it) }
        }

        setContent {
            val state: SubredditState by viewModel.state.collectAsState()
            val title = state.subreddit?.displayNamePrefixed ?: getString(R.string.app_name)

            BaseScreen(title = title, allowBackNavigation = false) {
                if (state.isLoadingFirstLoadPage) {
                    Box (modifier = Modifier.fillMaxSize()) {
                        Loader(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    PostList(
                        subreddit = subreddit,
                        viewModel = viewModel,
                        state = state
                    )
                }
            }
        }
    }
}

@Composable
fun PostList(subreddit: String? = null, viewModel: SubredditViewModel, state: SubredditState) {
    val scrollState = rememberScrollState()

    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            state.subreddit?.bannerBackgroundImage?.let {
                SubredditBanner(bannerUrl = it)
            }
        }

        state.posts.forEach() { post ->
            item {
                MinimalPostContainer(post = post)
            }
        }

        if (state.isLoadingNextPage) {
            item {
                Loader(modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }

        item {
            LaunchedEffect(!scrollState.canScrollForward) {
                viewModel.fetchPage(subreddit)
            }
        }
    }
}
