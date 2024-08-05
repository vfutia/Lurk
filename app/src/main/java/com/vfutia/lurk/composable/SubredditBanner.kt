package com.vfutia.lurk.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun SubredditBanner(bannerUrl: String) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        model = bannerUrl,
        contentDescription = "Subreddit banner"
    )
}