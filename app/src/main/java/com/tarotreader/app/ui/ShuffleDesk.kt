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
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.model.Draw
import com.tarotreader.app.model.SomeViewModel
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard

@Composable
fun ShuffleDeck (
//    chatViewModel: ChatViewModel,
    someViewModel: SomeViewModel,
    n: Int,
    modifier: Modifier = Modifier,
    flip: (Int, Int) -> Unit,
    postback: (List<TarotCard>) -> Unit
) {
    var flippedCardCounter by remember { mutableStateOf(0) }
//    val drawVal = chatViewModel.messages[n].draw

    val draw = someViewModel.draw
    fun flipCard() {
        flippedCardCounter++
        if (flippedCardCounter == draw.spread.nCards) {
//            postback(cards)
        }
    }

    when (draw.spread) {
        Spread.SINGLE_CARD -> SingleCardLayout(drawn = draw.listOfCards, postback = ::flipCard)
        Spread.THREE_CARDS -> ThreeCardsLayout(draw = draw, n, flip, postback = ::flipCard)
        Spread.PYRAMID -> PyramidLayout(drawn = draw.listOfCards, modifier = modifier, postback = ::flipCard)
        Spread.BROKEN_HEART -> BrokenHeartLayout(drawn = draw.listOfCards, postback = ::flipCard)
        Spread.HEALING_HEARTS -> HealingHeartsLayout(drawn = draw.listOfCards, postback = ::flipCard)
    }

//    when (drawVal?.spread) {
//        Spread.SINGLE_CARD -> SingleCardLayout(drawn = drawVal.listOfCards, postback = ::flipCard)
//        Spread.THREE_CARDS -> ThreeCardsLayout(draw = drawVal, n, flip, postback = ::flipCard)
//        Spread.PYRAMID -> PyramidLayout(drawn = drawVal.listOfCards, modifier = modifier, postback = ::flipCard)
//        Spread.BROKEN_HEART -> BrokenHeartLayout(drawn = drawVal.listOfCards, postback = ::flipCard)
//        Spread.HEALING_HEARTS -> HealingHeartsLayout(drawn = drawVal.listOfCards, postback = ::flipCard)
//        null -> TODO()
//    }
}

@Composable
fun ThreeCardsLayout(
    draw: Draw,
    n: Int,
    flip: (Int, Int) -> Unit,
    postback: () -> Unit
) {
    val drawM by remember {
        mutableStateOf(draw)
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
            items(draw.listOfCards.size) { index ->
                val maxWidth = (1 / draw.listOfCards.size).toFloat()
                RotatableCard(
                    card = draw.listOfCards[index],
                    rotatedState = drawM.cardsFlipState[index],
                    flip = { flip(n, index) },
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