package com.vfutia.lurk.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.vfutia.lurk.R
import com.vfutia.lurk.model.Image
import com.vfutia.lurk.model.ImageSource
import com.vfutia.lurk.model.Post
import com.vfutia.lurk.ui.theme.LurkTheme
import com.vfutia.lurk.ui.theme.Typography

@Composable
fun MinimalPostContainer(post: Post) {
    val constraints = ConstraintSet {
        val title = createRefFor("title")
        val author = createRefFor("author")
        val subreddit = createRefFor("subreddit")
        val upVotes = createRefFor("upvotes")
        val downVotes = createRefFor("downvotes")
        val score = createRefFor("score")
        val preview = createRefFor("preview")

        constrain(subreddit) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }

        constrain(title) {
            top.linkTo(subreddit.bottom)
            start.linkTo(subreddit.start)
        }

        constrain(preview) {
            top.linkTo(title.bottom)
            start.linkTo(title.start)
        }

        constrain(upVotes) {
            top.linkTo(preview.bottom)
            start.linkTo(preview.start)
        }

        constrain(downVotes) {
            top.linkTo(preview.bottom)
            start.linkTo(upVotes.end)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        constraintSet = constraints
    ) {
        Text(
            modifier = Modifier.layoutId("subreddit"),
            style = Typography.labelMedium,
            text = "r/${post.subreddit}"
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

        Text(
            modifier = Modifier.layoutId("upvotes"),
            style = Typography.labelMedium,
            text = post.ups.toString()
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