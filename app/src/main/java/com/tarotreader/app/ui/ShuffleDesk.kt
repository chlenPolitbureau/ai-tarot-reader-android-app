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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tarotreader.app.model.CardViewModel
import com.tarotreader.app.model.CardViewModelFactory
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.model.Draw
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard

@Composable
fun ShuffleDeck (
    draw: Draw,
    modifier: Modifier = Modifier,
    postback: (list: List<TarotCard>) -> Unit = {},
    chatViewModel: ChatViewModel? = null,
) {
    var flippedCardCounter by remember { mutableStateOf(0) }
    val drawn = draw.draw()
    val cardViewModelFactory = CardViewModelFactory(draw.spread)
//    val cardViewModel = ViewModelProvider(this, cardViewModelFactory)[CardViewModel::class.java]

    chatViewModel?.updateSelectedCards(drawn)

    fun flipCard() {
        flippedCardCounter++
        if (flippedCardCounter == drawn.size) {
            postback(drawn)
        }
    }

    when (draw.spread) {
        Spread.SINGLE_CARD -> SingleCardLayout(drawn = drawn, postback = ::flipCard)
        Spread.THREE_CARDS -> ThreeCardsLayout(drawn = drawn, postback = ::flipCard)
        Spread.PYRAMID -> PyramidLayout(drawn = drawn, modifier = modifier, postback = ::flipCard)
        Spread.BROKEN_HEART -> BrokenHeartLayout(drawn = drawn, postback = ::flipCard)
        Spread.HEALING_HEARTS -> HealingHeartsLayout(drawn = drawn, postback = ::flipCard)
    }
}

@Preview
@Composable
fun ShuffleDeskPreview() {
    ShuffleDeck(
        draw = Draw(spread = Spread.PYRAMID),
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun ThreeCardsLayout(
    drawn: List<TarotCard>,
    modifier: Modifier = Modifier,
    postback: () -> Unit,
    cardViewModel: CardViewModel? = null
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
//            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FlippableCard(
                    front_img = drawn[0].img,
                    modifier = Modifier,
                    postback = postback
                )
            }
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FlippableCard(
                    front_img = drawn[1].img,
                    postback = postback
                )
                FlippableCard(
                    front_img = drawn[2].img,
                    postback = postback
                )
            }
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlippableCard(
                    front_img = drawn[3].img,
                    postback = postback
                )
                FlippableCard(
                    front_img = drawn[4].img,
                    postback = postback
                )
                FlippableCard(
                    front_img = drawn[5].img,
                    postback = postback
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
                        postback = postback
                    )
                    FlippableCard(
                        front_img = drawn[3].img,
                        modifier = Modifier,
                        postback = postback
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
                        modifier = Modifier
                    )
                    FlippableCard(
                        front_img = drawn[5].img,
                        modifier = Modifier,
                        postback = postback
                    )
                    FlippableCard(
                        front_img = drawn[6].img,
                        modifier = Modifier,
                        postback = postback
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
                        postback = postback
                    )
                    FlippableCard(
                        front_img = drawn[2].img,
                        modifier = Modifier,
                        postback = postback
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
                    postback = postback
                )
                FlippableCard(
                    front_img = drawn[1].img,
                    postback = postback
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlippableCard(
                    front_img = drawn[2].img,
                    postback = postback
                )
                FlippableCard(
                    front_img = drawn[3].img,
                    postback = postback
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlippableCard(
                    front_img = drawn[4].img,
                    postback = postback
                )
                FlippableCard(
                    front_img = drawn[5].img,
                    postback = postback
                )
            }
        }
    }
}