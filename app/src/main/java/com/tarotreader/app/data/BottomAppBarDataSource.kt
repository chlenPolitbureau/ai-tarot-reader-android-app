package com.tarotreader.app.data

import androidx.annotation.StringRes
import com.tarotreader.app.R
import com.tarotreader.app.model.BottomBarMenuItem

object BottomAppBarDataSource {
    val items = listOf(
        BottomBarMenuItem(
            title = R.string.Reading,
            icon = R.drawable.playing_cards_svgrepo_com,
            route = AppRoutes.Chat.name
        ),
        BottomBarMenuItem(
            title = R.string.Learn,
            icon = R.drawable.learn_svgrepo_com,
            route = AppRoutes.Chat.name
        ),
        BottomBarMenuItem(
            title = R.string.Diary,
            icon = R.drawable.diary_svgrepo_com,
            route = AppRoutes.Test.name
        )
    )
}

enum class AppRoutes(@StringRes val title: Int) {
    Intro(title = R.string.intro),
    Main(title = R.string.main),
    Chat(title = R.string.chat),
    Test(title = R.string.test)
}