package com.tarotreader.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.tarotreader.app.R
import com.tarotreader.app.model.TarotCard

@Composable
fun ShuffleDeck (
    imageList: List<TarotCard>
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (lazyRow) = createRefs()
        LazyRow(
            modifier = Modifier
                .constrainAs(lazyRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(imageList.size) { index ->
                val maxWidth = 1 / imageList.size
                FlippableCard(
                    front_img = imageList[index].img,
                    modifier = Modifier
                        .fillParentMaxWidth(.33f)
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun ShuffleDeskPreview() {
    ShuffleDeck(
        imageList = listOf(
            TarotCard.Death,
            TarotCard.The_Devil,
            TarotCard.The_Empress
        )
    )
}