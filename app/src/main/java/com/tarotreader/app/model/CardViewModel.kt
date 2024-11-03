package com.tarotreader.app.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tarotreader.app.R

class CardViewModel(): ViewModel() {
    var cardRotated = mutableStateOf(false)
//    var rotatedState = List(draw.size) { false }.toMutableList()

    fun flipCard () {
        cardRotated.value = !cardRotated.value
    }
}