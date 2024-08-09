package com.vfutia.lurk.subreddit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.vfutia.lurk.BaseActivity
import com.vfutia.lurk.R
import com.vfutia.lurk.composable.BaseScreen
import com.vfutia.lurk.composable.Loader
import com.vfutia.lurk.composable.MenuHeader
import com.vfutia.lurk.composable.MenuItem
import com.vfutia.lurk.composable.MinimalPostContainer
import com.vfutia.lurk.composable.SubredditBanner
import com.vfutia.lurk.favorite.FavoriteState
import com.vfutia.lurk.favorite.FavoriteViewModel
import com.vfutia.lurk.model.Favorite
import com.vfutia.lurk.model.Post
import com.vfutia.lurk.post.PostActivity
import com.vfutia.lurk.setContentAndStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubredditActivity : BaseActivity() {
    companion object {
        const val KEY_SUBREDDIT = "subreddit"

        fun launchIntent(context: Context, subreddit: String? = null): Intent {
            return Intent(context, SubredditActivity::class.java).apply {
                putExtra(KEY_SUBREDDIT, subreddit)
            }
        }
    }

    private val subredditViewModel: SubredditViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val subreddit = intent.extras?.getString(KEY_SUBREDDIT)

        subredditViewModel.fetchPage(subreddit)
        favoriteViewModel.fetchFavorites()

        subreddit?.let { subredditViewModel.fetchSubreddit(subreddit) }

        setContentAndStatusBar {
            val subredditState: SubredditState by subredditViewModel.state.collectAsState()
            val favoriteState: FavoriteState by favoriteViewModel.state.collectAsState()
            val title = subredditState.subreddit?.displayNamePrefixed ?: getString(R.string.app_name)

            BaseScreen(
                title = title,
                allowBackNavigation = subreddit != null,
                drawerSheet = drawerSheet(
                    state = favoriteState,
                    onFavoriteClick = { newSubreddit -> startActivity(launchIntent(this@SubredditActivity, newSubreddit)) }
                ),
                actions = subreddit?.let { actions(favoriteViewModel, it, favoriteState) } ?: { }
            ) {
                if (subredditState.isLoadingFirstLoadPage) {
                    Box (modifier = Modifier.fillMaxSize()) {
                        Loader(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    PostList(
                        subreddit = subreddit,
                        viewModel = subredditViewModel,
                        state = subredditState,
                        onSubredditClick = { newSubreddit ->
                            startActivity(launchIntent(this, newSubreddit))
                        },
                        onPostClick = { post ->
                            startActivity(PostActivity.launchIntent(this, post))
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //Refresh the favorites if we're coming back
        favoriteViewModel.fetchFavorites()
    }
}

@Composable
private fun drawerSheet(state: FavoriteState, onFavoriteClick: (String) -> Unit) : @Composable ColumnScope.() -> Unit = {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item { MenuHeader(title = "Favorites") }
        state.favorites.forEach { favorite ->
            item {
                MenuItem(
                    subreddit = favorite.subreddit,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Composable
private fun actions(viewModel: FavoriteViewModel, subreddit: String, state: FavoriteState): @Composable RowScope.() -> Unit = {
    if (state.favorites.contains(Favorite(subreddit))) {
        IconButton(onClick = { viewModel.deleteFavorite(subreddit) }) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Unfavorite subreddit",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    } else {
        IconButton(onClick = { viewModel.addFavorite(subreddit) }) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite subreddit",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun PostList(
    subreddit: String? = null,
    viewModel: SubredditViewModel,
    state: SubredditState,
    onSubredditClick: (String) -> Unit,
    onPostClick: (Post) -> Unit
) {
    val scrollState = rememberScrollState()

    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            state.subreddit?.let {
                SubredditBanner(
                    bannerUrl = it.bannerBackgroundImage,
                    headerSize = it.bannerSize
                )
            }
        }

        state.posts.forEach() { post ->
            item {
                MinimalPostContainer(
                    isFrontPage = subreddit == null,
                    onSubredditClick = onSubredditClick,
                    onPostClick = onPostClick,
                    post = post
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = dimensionResource(id = R.dimen.divider_thickness)
                )
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
