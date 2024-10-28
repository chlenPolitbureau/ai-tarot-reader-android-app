package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tarotreader.app.R

@Composable
fun FlippableCard (
    @DrawableRes front_img: Int,
    @DrawableRes back_img: Int = R.drawable.backside,
    modifier: Modifier = Modifier,
    postback: () -> Unit = {}
) {
    var rotated by remember { mutableStateOf(false) }
    var img by remember { mutableStateOf(back_img) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(700)
    )

    fun flip() {
        rotated = !rotated
        postback()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (rotation > 90f) {
            img = front_img
        } else {
            img = back_img
        }
        Image(
            painter = painterResource(img),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .clickable { if (!rotated) flip() }
                .clip(RoundedCornerShape(4.dp))
                .graphicsLayer {
                    rotationY = rotation
                }
        )
    }
}

@Preview
@Composable
fun CardWithButtonPreview() {
    FlippableCard(
        front_img = R.drawable.w01,
        back_img = R.drawable.backside
    )
}