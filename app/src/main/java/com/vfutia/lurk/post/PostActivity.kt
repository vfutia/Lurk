package com.vfutia.lurk.post

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.vfutia.lurk.BaseActivity
import com.vfutia.lurk.R
import com.vfutia.lurk.composable.BaseScreen
import com.vfutia.lurk.composable.WebViewComposable
import com.vfutia.lurk.model.Post
import com.vfutia.lurk.setContentAndStatusBar
import com.vfutia.lurk.ui.theme.Typography

class PostActivity : BaseActivity() {

    private val postViewModel: PostViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()

    companion object {
        const val KEY_POST = "post"

        fun launchIntent(context: Context, post: Post): Intent {
            return Intent(context, PostActivity::class.java).apply {
                putExtra(KEY_POST, post)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val post = intent.extras?.getParcelable<Post>(KEY_POST)
            ?: throw IllegalArgumentException("post is null")

        setContentAndStatusBar {
            BaseScreen(title = post.title) {
                PostContainer(
                    modifier = Modifier.fillMaxSize(),
                    post = post
                )
            }
        }
    }
}

@Composable
fun PostContainer(modifier: Modifier, post: Post) {
    when {
        post.isSelf -> {
            SelfPost(
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                post = post
            )
        }
        post.isVideo -> {  }
        else -> {
            WebViewComposable(
                modifier = modifier.fillMaxSize(),
                url = post.url
            )
        }
    }
}

@Composable
fun SelfPost (modifier: Modifier, post: Post) {
    Box(modifier = modifier) {
        Text (
            text = post.title ?: "",
            style = Typography.headlineSmall,
        )

        Text (
            text = post.selftext ?: "",
            style = Typography.bodyLarge,
        )
    }
}