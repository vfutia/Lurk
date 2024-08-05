package com.vfutia.lurk.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.vfutia.lurk.R
import com.vfutia.lurk.favorite.FavoriteState
import com.vfutia.lurk.ui.theme.Typography

@Composable
fun MenuItem (
    subreddit: String,
    onFavoriteClick: (String) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .clickable { onFavoriteClick(subreddit) },
        text = "r/${subreddit}",
        style = Typography.labelMedium,
    )
}