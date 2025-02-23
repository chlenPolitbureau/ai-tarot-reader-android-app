package com.tarotreader.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.model.Draw
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard


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
        if (draw != null) {
            when (draw.spread) {
                Spread.SINGLE_CARD -> SingleCardLayout(draw = draw, chatViewModel, flipState, n, postback = ::flipCard)
                Spread.THREE_CARDS -> ThreeCardsLayout(draw = draw, chatViewModel, flipState, n, postback = ::flipCard)
                Spread.PYRAMID -> PyramidLayout(draw = draw, chatViewModel, flipState, n, postback = ::flipCard)
                Spread.BROKEN_HEART -> BrokenHeartLayout(draw = draw, chatViewModel, flipState, n, postback = ::flipCard)
                Spread.HEALING_HEARTS -> HealingHeartsLayout(draw = draw, chatViewModel, flipState, n, postback = ::flipCard)
            }
        }
    }

@Composable
fun SingleCardLayout(
    draw: Draw,
    chatViewModel: ChatViewModel,
    flipState: List<Boolean>,
    n: Int,
    postback: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (lazyRow) = createRefs()
        LazyRow(
            modifier = modifier
                .constrainAs(lazyRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items(draw.listOfCards.size) { index ->
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
                    postback = postback
                )
            }
        }
    }
}

@Composable
fun ThreeCardsLayout(
    draw: Draw,
    chatViewModel: ChatViewModel,
    flipState: List<Boolean>,
    n: Int,
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
            items(draw.listOfCards.size) { index ->
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
                    postback = postback
                )
            }
        }
    }
}

@Composable
fun PyramidLayout (
    draw: Draw,
    chatViewModel: ChatViewModel,
    flipState: List<Boolean>,
    n: Int,
    postback: () -> Unit,
    modifier: Modifier = Modifier.padding(
        top = 4.dp,
        bottom = 4.dp
    )
) {
    Column {
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            RotatableCard(
                card = draw.listOfCards[0],
                rotatedState = flipState[0],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 0
                ) },
                modifier = Modifier,
                postback = postback
            )
        }
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RotatableCard(
                card = draw.listOfCards[1],
                rotatedState = flipState[1],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 1
                ) },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[2],
                rotatedState = flipState[2],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 2
                ) },
                modifier = Modifier,
                postback = postback
            )
        }
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RotatableCard(
                card = draw.listOfCards[3],
                rotatedState = flipState[3],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 3
                ) },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[4],
                rotatedState = flipState[4],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 4
                ) },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[5],
                rotatedState = flipState[5],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 5
                ) },
                modifier = Modifier,
                postback = postback
            )
        }
    }
}

@Composable
fun BrokenHeartLayout (
    draw: Draw,
    chatViewModel: ChatViewModel,
    flipState: List<Boolean>,
    n: Int,
    postback: () -> Unit,
    modifier: Modifier = Modifier.padding(
        top = 8.dp,
        bottom = 8.dp
    )
) {
    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.heightIn(min = 500.dp)
        ) {
            RotatableCard(
                card = draw.listOfCards[0],
                rotatedState = flipState[0],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 0
                ) },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[2],
                rotatedState = flipState[2],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 3
                ) },
                modifier = Modifier,
                postback = postback
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.heightIn(min = 520.dp)
        ) {
            RotatableCard(
                card = draw.listOfCards[4],
                rotatedState = flipState[4],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 0
                ) },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[5],
                rotatedState = flipState[5],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 3
                ) },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[6],
                rotatedState = flipState[6],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 3
                ) },
                modifier = Modifier,
                postback = postback
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column (
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.heightIn(min = 500.dp)
        ) {
            RotatableCard(
                card = draw.listOfCards[1],
                rotatedState = flipState[1],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 1
                ) },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[3],
                rotatedState = flipState[3],
                flip = { chatViewModel.flipCard(
                    nMessage = n,
                    index = 2
                ) },
                modifier = Modifier,
                postback = postback
            )
        }
    }
}

@Composable
fun HealingHeartsLayout (
    draw: Draw,
    chatViewModel: ChatViewModel,
    flipState: List<Boolean>,
    n: Int,
    postback: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier.heightIn(min = 560.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            RotatableCard(
                card = draw.listOfCards[0],
                rotatedState = flipState[0],
                flip = {
                    chatViewModel.flipCard(
                        nMessage = n,
                        index = 0
                    )
                },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[2],
                rotatedState = flipState[2],
                flip = {
                    chatViewModel.flipCard(
                        nMessage = n,
                        index = 2
                    )
                },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[4],
                rotatedState = flipState[4],
                flip = {
                    chatViewModel.flipCard(
                        nMessage = n,
                        index = 4
                    )
                },
                modifier = Modifier,
                postback = postback
            )
        }
        Spacer(modifier = Modifier.width(40.dp))
        Column(
            modifier = modifier.heightIn(min = 560.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            RotatableCard(
                card = draw.listOfCards[1],
                rotatedState = flipState[1],
                flip = {
                    chatViewModel.flipCard(
                        nMessage = n,
                        index = 1
                    )
                },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[3],
                rotatedState = flipState[3],
                flip = {
                    chatViewModel.flipCard(
                        nMessage = n,
                        index = 3
                    )
                },
                modifier = Modifier,
                postback = postback
            )
            RotatableCard(
                card = draw.listOfCards[5],
                rotatedState = flipState[5],
                flip = {
                    chatViewModel.flipCard(
                        nMessage = n,
                        index = 5
                    )
                },
                modifier = Modifier,
                postback = postback
            )
        }
    }
}
