package com.tarotreader.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.tarotreader.app.model.ChatViewModel


@Composable
fun Shuffle(
    n: Int,
    chatViewModel: ChatViewModel,
    postback: () -> Unit
) {
    val draw = chatViewModel.messages[n].draw
    val flipState = remember { chatViewModel.listOfCardStates[n] }
    var flippedCardCounter by remember { mutableStateOf(0) }

    fun flipCard() {
        flippedCardCounter++
        if (flippedCardCounter == draw?.spread?.nCards) {
            postback()
        }
    }

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
            if (draw != null) {
                items(draw.listOfCards.size) { index ->
                    val maxWidth = (1 / draw.listOfCards.size).toFloat()
                    RotatableCard(
                        card = draw.listOfCards[index],
                        rotatedState = flipState[index],
                        flip = { chatViewModel.flipCard(
                            nMessage = n,
                            index = index
                        ) },
                        modifier = Modifier
                            .fillParentMaxWidth(.33f)
                            .aspectRatio(1f),
                        postback = ::flipCard
                    )
                }
            }
        }
    }
}