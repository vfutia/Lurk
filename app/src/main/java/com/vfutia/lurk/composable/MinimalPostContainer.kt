package com.vfutia.lurk.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.vfutia.lurk.R
import com.vfutia.lurk.model.Post
import com.vfutia.lurk.ui.theme.Typography

@Composable
fun MinimalPostContainer(post: Post) {
    val constraints = ConstraintSet {
        val title = createRefFor("title")

        constrain(title) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        constraintSet = constraints
    ) {
        Text(
            modifier = Modifier.layoutId("title"),
            style = Typography.bodyLarge,
            text = post.title
        )
    }
}