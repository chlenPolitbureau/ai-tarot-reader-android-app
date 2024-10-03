package com.tarotreader.app.data

import com.tarotreader.app.R
import com.tarotreader.app.model.Feature

object FeaturesDataSource {
    val features = listOf(
        Feature(title = "AI tarot reader",
            description = "Ask the cards your questions and get the reading prepared by the AI Reader",
            backgroundImage = R.drawable.fence),
        Feature(title = "Learn about tarot",
            description = "Deep dive into the world of tarot. Learn about cards, decks, spreads, meanings",
            backgroundImage = R.drawable.fence),
    )
}