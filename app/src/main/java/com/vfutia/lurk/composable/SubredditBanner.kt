package com.vfutia.lurk.composable

import android.util.Size
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun SubredditBanner(
    headerSize: Size,
    bannerUrl: String?
) {
    if (bannerUrl == null || headerSize.height == 0) { return }

    //use the width height ratio of the banner and the device width to
    //determine optimal image composable size
    val heightWidthRatio = (headerSize.width.toFloat() / headerSize.height.toFloat())

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(heightWidthRatio, true),
        model = bannerUrl,
        contentDescription = "Subreddit banner",
        contentScale = ContentScale.FillBounds
    )
}