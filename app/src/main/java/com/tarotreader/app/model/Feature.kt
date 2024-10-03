package com.tarotreader.app.model

import androidx.annotation.DrawableRes

data class Feature(
    val title: String,
    val description: String,
    @DrawableRes val backgroundImage: Int
)