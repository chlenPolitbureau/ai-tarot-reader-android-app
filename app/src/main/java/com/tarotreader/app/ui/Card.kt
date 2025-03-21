package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tarotreader.app.R
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Purple40
import com.tarotreader.app.ui.theme.Typography

@Composable
fun RotatableCard(
    card: TarotCard,
    rotatedState: Boolean,
    id: Int,
    flip: () -> Unit,
    modifier: Modifier = Modifier,
    postback: () -> Unit = {}
) {
    var rotated by remember { mutableStateOf(rotatedState) }
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 360f else 180f,
        animationSpec = tween(700)
    )

    val verticalOrientation = if (card.orientation == -1) 180f else 0f

    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                if (rotation > 270f) card.img else card.cover_img
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .clickable {
                    rotated = !rotated
                    flip() // change rotatedState in viewmodel
                    postback() // incremental to counter
                }
                .clip(RoundedCornerShape(4.dp))
                .graphicsLayer {
                    rotationY = rotation
                    rotationZ = verticalOrientation
                }
            )
        if(!rotated) {
            Text(
                id.toString(),
                style = Typography.bodyMedium,
                color = Purple40,
                fontWeight = FontWeight.Bold
            )
        }
    }
}