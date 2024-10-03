package com.tarotreader.app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tarotreader.app.data.AppRoutes

data class BottomBarMenuItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
)
