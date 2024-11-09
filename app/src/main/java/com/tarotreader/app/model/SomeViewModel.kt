package com.tarotreader.app.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class SomeViewModel: ViewModel() {
    var backgroundColor by mutableStateOf(Color.White)
        private set
    val draw by mutableStateOf(Draw(
        spread = Spread.THREE_CARDS,
        listOfCards = listOf(
            TarotCard.The_Fool,
            TarotCard.The_Hanged_Man,
            TarotCard.The_Hermit
        )
    ))

    var cardState = mutableStateListOf<Boolean>()

    init {
        cardState.addAll(listOf(false, false, false))
    }

    fun changeBackgroundColor() {
        backgroundColor = Color.Red
    }

    fun flipDrawCard(n: Int) {
        draw.cardsFlipState[n] = !draw.cardsFlipState[n]
    }

    fun flipOneCard(n: Int) {
        cardState.set(
            n, !cardState[n]
        )
    }

    fun flipCard(n: Int, m: Int) {
        cardState.set(
            m, !cardState[m]
        )
    }
}
