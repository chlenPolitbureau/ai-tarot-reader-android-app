package com.tarotreader.app.data

import com.tarotreader.app.Chat
import com.tarotreader.app.Content
import com.tarotreader.app.Journal
import com.tarotreader.app.Learn
import com.tarotreader.app.R
import com.tarotreader.app.model.DailyAdvice
import com.tarotreader.app.model.Spread
import com.tarotreader.app.ui.theme.bkgrndGrey

object DailyAdviceSource {
    val listOfAdvice = listOf(
        DailyAdvice(
            img = R.drawable.lovespreadthumbnail,
            header = "Love Spread",
            bkgrndColor = bkgrndGrey,
            route = Chat(
                spread = Spread.BROKEN_HEART
            )
        ),
        DailyAdvice(
            img = R.drawable.learnspreadsthumbnail,
            header = "Learn about Spreads",
            bkgrndColor = bkgrndGrey,
            route = Learn(
                startingTab = 1
            )
        ),
        DailyAdvice(
            img = R.drawable.predictionsreviewthumbnail,
            header = "Review past readings",
            bkgrndColor = bkgrndGrey,
            route = Journal
        ),
        DailyAdvice(
            img = R.drawable.rewievpredictionsthumbnail,
            header = "Blog post 1",
            bkgrndColor = bkgrndGrey,
            route = Learn(
                startingTab = 2
            )
        )
    )
}