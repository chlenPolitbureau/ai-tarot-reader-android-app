package com.tarotreader.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard

@Composable
fun ShuffleDeck (
    spread: Spread,
    cards: List<TarotCard>,
    modifier: Modifier = Modifier,
    postback: (list: List<TarotCard>) -> Unit = {},
    appViewModel: AppViewModel? = null,
) {
    var flippedCardCounter by remember { mutableStateOf(0) }

    appViewModel?.updateSelectedCards(cards)

    fun flipCard() {
        flippedCardCounter++
        if (flippedCardCounter == cards.size) {
            postback(cards)
        }
    }

    when (spread) {
        Spread.SINGLE_CARD -> SingleCardLayout(drawn = cards, postback = ::flipCard)
        Spread.THREE_CARDS -> ThreeCardsLayout(drawn = cards, postback = ::flipCard)
        Spread.PYRAMID -> PyramidLayout(drawn = cards, modifier = modifier, postback = ::flipCard)
        Spread.BROKEN_HEART -> BrokenHeartLayout(drawn = cards, postback = ::flipCard)
        Spread.HEALING_HEARTS -> HealingHeartsLayout(drawn = cards, postback = ::flipCard)
    }
}

@Composable
fun ThreeCardsLayout(
    drawn: List<TarotCard>,
    postback: () -> Unit
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
            items(drawn.size) { index ->
                val maxWidth = (1 / drawn.size).toFloat()
                FlippableCard(
                    front_img = drawn[index].img,
                    modifier = Modifier
                        .fillParentMaxWidth(.33f)
                        .aspectRatio(1f),
                    postback = postback
                )
            }
        }
    }
}

@Composable
fun SingleCardLayout(
    drawn: List<TarotCard>,
    postback: () -> Unit,
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
            horizontalArrangement = Arrangement.Center
        ) {
            items(drawn.size) { index ->
                val maxWidth = (1 / drawn.size).toFloat()
                FlippableCard(
                    front_img = drawn[index].img,
//                    rotate = cardViewModel.rotatedState[0],
//                    flipPostback = { cardViewModel.flipCard(0) },
                    modifier = Modifier
                        .fillParentMaxWidth(.33f)
                        .aspectRatio(1f),
                    postback = postback
                )
            }
        }
    }
}

@Composable
fun PyramidLayout (
    drawn: List<TarotCard>,
    modifier: Modifier = Modifier,
    postback: () -> Unit
) {
        Column (
        ) {
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FlippableCard(
                    front_img = drawn[0].img,
                    modifier = Modifier,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[0],
//                    flipPostback = { cardViewModel.flipCard(0) }
                )
            }
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FlippableCard(
                    front_img = drawn[1].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[1],
//                    flipPostback = { cardViewModel.flipCard(1) }
                )
                FlippableCard(
                    front_img = drawn[2].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[2],
//                    flipPostback = { cardViewModel.flipCard(2) }
                )
            }
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlippableCard(
                    front_img = drawn[3].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[3],
//                    flipPostback = { cardViewModel.flipCard(3) }
                )
                FlippableCard(
                    front_img = drawn[4].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[4],
//                    flipPostback = { cardViewModel.flipCard(4) }
                )
                FlippableCard(
                    front_img = drawn[5].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[5],
//                    flipPostback = { cardViewModel.flipCard(5) }
                )
            }
        }
    }

@Composable
fun BrokenHeartLayout (
    drawn: List<TarotCard>,
    postback: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (lazyRow) = createRefs()
        Column (
            modifier = Modifier.constrainAs(lazyRow) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Row {
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            top = 20.dp,
                            bottom = 20.dp
                        ),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    FlippableCard(
                        front_img = drawn[0].img,
                        modifier = Modifier,
                        postback = postback,
//                        rotate = cardViewModel.rotatedState[0],
//                        flipPostback = { cardViewModel.flipCard(0) }
                    )
                    FlippableCard(
                        front_img = drawn[3].img,
                        modifier = Modifier,
                        postback = postback,
//                        rotate = cardViewModel.rotatedState[3],
//                        flipPostback = { cardViewModel.flipCard(3) }
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            top = 20.dp,
                            bottom = 20.dp
                        ),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    FlippableCard(
                        front_img = drawn[4].img,
                        modifier = Modifier,
//                        rotate = cardViewModel.rotatedState[4],
//                        flipPostback = { cardViewModel.flipCard(4) }
                    )
                    FlippableCard(
                        front_img = drawn[5].img,
                        modifier = Modifier,
                        postback = postback,
//                        rotate = cardViewModel.rotatedState[5],
//                        flipPostback = { cardViewModel.flipCard(5) }
                    )
                    FlippableCard(
                        front_img = drawn[6].img,
                        modifier = Modifier,
                        postback = postback,
//                        rotate = cardViewModel.rotatedState[6],
//                        flipPostback = { cardViewModel.flipCard(6) }
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            top = 20.dp,
                            bottom = 20.dp
                        ),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    FlippableCard(
                        front_img = drawn[1].img,
                        modifier = Modifier,
                        postback = postback,
//                        rotate = cardViewModel.rotatedState[1],
//                        flipPostback = { cardViewModel.flipCard(1) }
                    )
                    FlippableCard(
                        front_img = drawn[2].img,
                        modifier = Modifier,
                        postback = postback,
//                        rotate = cardViewModel.rotatedState[2],
//                        flipPostback = { cardViewModel.flipCard(2) }
                    )
                }
            }
        }
    }
}

@Composable
fun HealingHeartsLayout (
    drawn: List<TarotCard>,
    modifier: Modifier = Modifier,
    postback: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (lazyRow) = createRefs()
        Column {
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlippableCard(
                    front_img = drawn[0].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[0],
//                    flipPostback = { cardViewModel.flipCard(0) }
                )
                FlippableCard(
                    front_img = drawn[1].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[1],
//                    flipPostback = { cardViewModel.flipCard(1) }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlippableCard(
                    front_img = drawn[2].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[2],
//                    flipPostback = { cardViewModel.flipCard(2) }
                )
                FlippableCard(
                    front_img = drawn[3].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[3],
//                    flipPostback = { cardViewModel.flipCard(3) }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlippableCard(
                    front_img = drawn[4].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[4],
//                    flipPostback = { cardViewModel.flipCard(4) }
                )
                FlippableCard(
                    front_img = drawn[5].img,
                    postback = postback,
//                    rotate = cardViewModel.rotatedState[5],
//                    flipPostback = { cardViewModel.flipCard(5) }
                )
            }
        }
    }
}