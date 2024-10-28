package com.tarotreader.app.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tarotreader.app.R

class CardViewModel(
    spread: Spread
): ViewModel() {
    val draw = Draw(spread).draw()
    var rotatedState = List(draw.size) { false }.toMutableList()

    fun flipCard(index: Int) {
        rotatedState[index] = !rotatedState[index]
    }
}