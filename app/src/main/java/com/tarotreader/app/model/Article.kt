package com.tarotreader.app.model

import androidx.annotation.DrawableRes

class Article(
    @DrawableRes val img: Int,
    val id: String,
    val header: String,
    val previewText: String,
    val fullText: String
)