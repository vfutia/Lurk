package com.vfutia.lurk.composable

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.vfutia.lurk.R
import com.vfutia.lurk.extension.isValidUrl
import com.vfutia.lurk.extension.toUpvoteString
import com.vfutia.lurk.model.Image
import com.vfutia.lurk.model.ImageSource
import com.vfutia.lurk.model.Post
import com.vfutia.lurk.ui.theme.LurkTheme
import com.vfutia.lurk.ui.theme.Typography

@Composable
fun MinimalPostContainer(
    isFrontPage: Boolean = true,
    post: Post,
    onSubredditClick: (String) -> Unit = { },
    onUsernameClick: () -> Unit = { },
    onPostClick: () -> Unit = { }
) {
    val iconInternalMargin = dimensionResource(id = R.dimen.padding_tiny)
    val titleMargin = if (post.thumbnail.isValidUrl()) {
        dimensionResource(id = R.dimen.padding_medium)
    } else {
        0.dp
    }
    val thumbnailSize = dimensionResource(id = R.dimen.thumbnail_size)
    val thumbnailDimension = if (post.thumbnail.isValidUrl()) {
        Dimension.value(dimensionResource(id = R.dimen.thumbnail_size))
    } else {
        Dimension.value(0.dp)
    }
    val spacerSize = dimensionResource(id = R.dimen.padding_small)

    val constraints = ConstraintSet {
        val titleSpacer = createRefFor("title-spacer")
        val author = createRefFor("author")
        val subreddit = createRefFor("subreddit")
        val title = createRefFor("title")
        val preview = createRefFor("preview")
        val upVoteIcon = createRefFor("upvote-icon")
        val upVotes = createRefFor("upvotes")
        val commentIcon = createRefFor("comment-icon")
        val comments = createRefFor("comments")
        val created = createRefFor("created")
        val iconSpacer = createRefFor("icon-spacer")
        val betweenIconSpacer = createRefFor("between-icon-spacer")

        constrain(subreddit) {
            top.linkTo(created.top)
            start.linkTo(parent.start)
            bottom.linkTo(created.bottom)
        }

        constrain(author) {
            top.linkTo(created.top)
            start.linkTo(parent.start)
            bottom.linkTo(created.bottom)
        }

        constrain(created) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(title.top)
        }

        constrain(titleSpacer) {
            top.linkTo(created.bottom)
            bottom.linkTo(title.top)
        }

        constrain(title) {
            width = Dimension.fillToConstraints
            top.linkTo(titleSpacer.bottom)
            start.linkTo(preview.end)
            bottom.linkTo(iconSpacer.top)
            end.linkTo(parent.end, margin = titleMargin)
        }

        constrain(preview) {
            width = thumbnailDimension
            height = thumbnailDimension
            top.linkTo(titleSpacer.bottom)
            start.linkTo(parent.start)
            end.linkTo(title.start, margin = titleMargin)
        }


        constrain(iconSpacer) {
            top.linkTo(title.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(upVoteIcon) {
            top.linkTo(iconSpacer.bottom)
            start.linkTo(parent.start)
            end.linkTo(upVotes.start, margin = iconInternalMargin)
        }
        
        constrain(upVotes) {
            top.linkTo(upVoteIcon.top)
            bottom.linkTo(upVoteIcon.bottom)
            start.linkTo(upVoteIcon.end)
            end.linkTo(betweenIconSpacer.start)
        }

        constrain(betweenIconSpacer) {
            top.linkTo(iconSpacer.top)
            start.linkTo(upVotes.end)
            end.linkTo(commentIcon.start)
        }

        constrain(commentIcon) {
            top.linkTo(iconSpacer.bottom)
            start.linkTo(betweenIconSpacer.end)
            end.linkTo(comments.start, margin = iconInternalMargin)
        }

        constrain(comments) {
            top.linkTo(commentIcon.top)
            bottom.linkTo(commentIcon.bottom)
            start.linkTo(commentIcon.end)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .clickable { onPostClick() }
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        constraintSet = constraints
    ) {
        if (isFrontPage) {
            Text(
                modifier = Modifier
                    .clickable { onSubredditClick(post.subreddit) }
                    .layoutId("subreddit"),
                style = Typography.labelMedium,
                text = "r/${post.subreddit}"
            )
        } else {
            Text(
                modifier = Modifier
                    .clickable { onUsernameClick() }
                    .layoutId("author"),
                style = Typography.labelMedium,
                text = "u/${post.author}"
            )
        }

        Text(
            modifier = Modifier.layoutId("created"),
            style = Typography.labelMedium,
            text = TimeAgo.using(post.created)
        )

        Spacer(modifier = Modifier
            .height(spacerSize)
            .layoutId("title-spacer")
        )

        if (post.thumbnail.isValidUrl()) {
            AsyncImage(
                modifier = Modifier
                    .layoutId("preview")
                    .size(thumbnailSize),
                model = post.thumbnail,
                contentDescription = "Post preview",
                contentScale = ContentScale.Fit,
                alignment = Alignment.TopStart
            )
        }

        Text(
            modifier = Modifier
                .layoutId("title")
                .fillMaxWidth()
                .defaultMinSize(minHeight = thumbnailSize),
            style = Typography.titleMedium,
            text = post.title
        )

        Spacer(modifier = Modifier
            .layoutId("icon-spacer")
            .height(dimensionResource(id = R.dimen.padding_medium))
        )

        Icon(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.post_icon_size))
                .layoutId("upvote-icon"),
            imageVector = Icons.Outlined.ThumbUp,
            contentDescription = "Upvote"
        )
        
        Text(
            modifier = Modifier.layoutId("upvotes"),
            style = Typography.labelMedium,
            text = post.ups.toUpvoteString()
        )

        Spacer(modifier = Modifier
            .layoutId("between-icon-spacer")
            .width(spacerSize)
        )

        Icon(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.post_icon_size))
                .layoutId("comment-icon"),
            imageVector = Icons.Outlined.Edit,
            contentDescription = "Comment"
        )

        Text(
            modifier = Modifier.layoutId("comments"),
            style = Typography.labelMedium,
            text = post.numComments.toString()
        )
    }
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Full", showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED)
@Composable
fun MinimalPostContainerPreview() {
    LurkTheme {
        Surface {
            MinimalPostContainer(post = Post (
                id = "asdfasf",
                title = "This is the title of the post",
                author = "someguy",
                created = System.currentTimeMillis(),
                downs = 5,
                ups = 10,
                score = 20000,
                isMeta = false,
                isVideo = false,
                over18 = false,
                pinned = false,
                subreddit = "test",
                url = "https://www.google.com",
                preview = com.vfutia.lurk.model.Preview(
                    listOf(Image(ImageSource(
                        url = "https://upload.wikimedia.org/wikipedia/commons/7/70/Example.png",
                        width = 172,
                        height = 178
                    )))),
                numComments = 20,
                thumbnail = "https://upload.wikimedia.org/wikipedia/commons/7/70/Example.png"
            ))
        }
    }
}