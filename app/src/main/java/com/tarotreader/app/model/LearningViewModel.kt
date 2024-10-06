package com.tarotreader.app.model

import androidx.lifecycle.ViewModel

class LearningViewModel: ViewModel() {
    val cards: List<TarotCard> = TarotCard.entries
    val suites: List<Suites> = Suites.entries

    fun filteredCards(suites: List<Suites>): List<TarotCard> {
        return cards.filter { it.suite in suites }
    }

    val spreads: List<Spread> = Spread.entries
    val spreadAffiliations: List<SpreadAffiliation> = SpreadAffiliation.entries
}