package com.tarotreader.app.data

import com.tarotreader.app.R
import com.tarotreader.app.model.Article

object ArticlesDataSource {
    val articles = listOf(
        Article(
            img = R.drawable.cow_animal_cow_head,
            header = "The Cow",
            previewText = "This is a sample article telling a lot of interesting and funny facts about cows.",
            fullText = "Cows are fascinating creatures with some unique habits. One of the most interesting is their rumination process. Unlike humans who have a single-chambered stomach, cows have a four-chambered stomach. This allows them to regurgitate their food and chew it again, a process known as rumination.\n" +
                    "\n" +
                    "Why do cows do this? It helps them break down the tough cellulose in their food, which is difficult to digest. Rumination also helps them to absorb more nutrients from their food. So, the next time you see a cow chewing, remember, it's not just enjoying its food; it's working hard to get the most out of it! "
        )
    )
}