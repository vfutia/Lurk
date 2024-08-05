package com.vfutia.lurk.composable

import android.content.res.Configuration
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vfutia.lurk.R
import com.vfutia.lurk.ui.theme.LurkInverse
import com.vfutia.lurk.ui.theme.LurkTheme

private const val BOUNCE_DURATION = 500

@Composable
fun Loader(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.secondary
    val radius = dimensionResource(id = R.dimen.loader_circle_radius)
    val height = radius.times(2)

    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_tiny))
    ) {
        val circle1Transition = rememberInfiniteTransition()
        val circle2Transition = rememberInfiniteTransition()
        val circle3Transition = rememberInfiniteTransition()

        val circle1Animation by circle1Transition.animateFloat(
            label = "bounce",
            initialValue = 0f,
            targetValue = height.value,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = BOUNCE_DURATION),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(0)
            )
        )

        val circle2Animation by circle2Transition.animateFloat(
            label = "bounce",
            initialValue = 0f,
            targetValue = height.value,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = BOUNCE_DURATION),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(250)
            )
        )

        val circle3Animation by circle3Transition.animateFloat(
            label = "bounce",
            initialValue = 0f,
            targetValue = height.value,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = BOUNCE_DURATION),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(500)
            )
        )

        Box(modifier = Modifier
            .offset(y = circle1Animation.dp)
            .size(height)
            .drawWithContent {
                drawCircle(
                    radius = radius.toPx(),
                    color = color,
                )
            })

        Box(modifier = Modifier
            .offset(y = circle2Animation.dp)
            .size(height)
            .drawWithContent {
                drawCircle(
                    radius = radius.toPx(),
                    color = color,
                )
            }
        )

        Box(modifier = Modifier
            .offset(y = circle3Animation.dp)
            .size(height)
            .drawWithContent {
                drawCircle(
                    radius = radius.toPx(),
                    color = color,
                )
            }
        )
    }
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoaderInversePreview() {
    LurkInverse {
        Surface {
            Loader()
        }
    }
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoaderPreview() {
    LurkTheme {
        Surface {
            Loader()
        }
    }
}
