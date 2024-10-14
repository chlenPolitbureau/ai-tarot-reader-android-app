package com.tarotreader.app.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BottomBarMenuItem(
    @StringRes val text: Int,
    @DrawableRes val icon: Int,
    val navigate: () -> Unit
)