package com.tarotreader.app.model

import androidx.lifecycle.ViewModel

class DrawViewModel(
    draw: Draw
): ViewModel() {
    val listOfCards = draw.listOfCards
    private val _cardsFlipState = draw.cardsFlipState
    val cardsFlipState = _cardsFlipState

    fun flipCard(index: Int) {
        _cardsFlipState[index] = !_cardsFlipState[index]
    }
}