package com.vfutia.lurk.composable

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.vfutia.lurk.R
import com.vfutia.lurk.extension.toUpvoteString
import com.vfutia.lurk.model.Image
import com.vfutia.lurk.model.ImageSource
import com.vfutia.lurk.model.Post
import com.vfutia.lurk.ui.theme.LurkTheme
import com.vfutia.lurk.ui.theme.Typography

@Composable
fun MinimalPostContainer(
    post: Post,
    onSubredditClick: (String) -> Unit = { },
    onUsernameClick: () -> Unit = { },
    onPostClick: () -> Unit = { }
) {
    val upVoteMargin = dimensionResource(id = R.dimen.padding_tiny)

    val constraints = ConstraintSet {
        val title = createRefFor("title")
        val author = createRefFor("author")
        val subreddit = createRefFor("subreddit")
        val upVoteIcon = createRefFor("upvote-icon")
        val upVotes = createRefFor("upvotes")
        val preview = createRefFor("preview")
        val created = createRefFor("created")

        constrain(subreddit) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }

        constrain(author) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }

        constrain(created) {
            top.linkTo(parent.top)
            end.linkTo(author.start)
        }

        constrain(title) {
            top.linkTo(subreddit.bottom)
            start.linkTo(subreddit.start)
        }

        constrain(preview) {
            top.linkTo(title.bottom)
            start.linkTo(title.start)
        }

        constrain(upVoteIcon) {
            top.linkTo(preview.bottom, margin = upVoteMargin)
            start.linkTo(parent.start)
        }
        
        constrain(upVotes) {
            top.linkTo(upVoteIcon.top)
            bottom.linkTo(upVoteIcon.bottom)
            start.linkTo(upVoteIcon.end, margin = upVoteMargin)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        constraintSet = constraints
    ) {
        Text(
            modifier = Modifier
                .clickable { onSubredditClick(post.subreddit) }
                .layoutId("subreddit"),
            style = Typography.labelMedium,
            text = "r/${post.subreddit}"
        )

        Text(
            modifier = Modifier.layoutId("created"),
            style = Typography.labelMedium,
            text = TimeAgo.using(post.created)
        )

        Text(
            modifier = Modifier
                .clickable { onUsernameClick() }
                .layoutId("author"),
            style = Typography.labelMedium,
            text = "u/${post.author}"
        )

        Text(
            modifier = Modifier.layoutId("title"),
            style = Typography.titleMedium,
            text = post.title
        )

        post.thumbnail?.let {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .layoutId("preview"),
                model = it,
                contentDescription = "Post preview"
            )
        }

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

        Text(
            modifier = Modifier.layoutId("downvotes"),
            style = Typography.labelMedium,
            text = post.downs.toString()
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
            MinimalPostContainer(Post (
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