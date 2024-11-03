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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tarotreader.app.R
import com.tarotreader.app.model.CardViewModel
import com.tarotreader.app.model.MaterialCard
import com.tarotreader.app.model.Spread

@Composable
fun FlippableCard (
    @DrawableRes front_img: Int,
    rotate: Boolean = false,
    index: Int = 0,
    @DrawableRes back_img: Int = R.drawable.backside,
    modifier: Modifier = Modifier,
    flipPostback: () -> Unit = {},
    postback: () -> Unit = {}
) {
    var img by remember { mutableStateOf(back_img) }

    val rotation by animateFloatAsState(
        targetValue = if (rotate) 180f else 0f,
        animationSpec = tween(700)
    )

    fun flip() {
        flipPostback()
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
                .clickable { if (!rotate) flip() }
                .clip(RoundedCornerShape(4.dp))
                .graphicsLayer {
                    rotationY = rotation
                }
        )
    }
}


@Composable
fun RotatableCard(
    card: MaterialCard,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (card.cardViewModel.cardRotated.value) 180f else 0f,
        animationSpec = tween(700)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                if (rotation > 90f) card.card.img else card.card.cover_img
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .clickable {
                    card.cardViewModel.flipCard()
                }
                .clip(RoundedCornerShape(4.dp))
                .graphicsLayer {
                    rotationY = rotation
                }
            )
    }
}