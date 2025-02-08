package com.tarotreader.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Currency (
    var type: CurrencyType,
    var amount: Long = 0
)

enum class CurrencyType {
    MANA, ENERGY, COINS
}