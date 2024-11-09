package com.tarotreader.app.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.tarotreader.app.R

enum class Suites {
    MAJOR,
    CUPS,
    PENTACLES,
    SWORDS,
    WANDS;

    override fun toString(): String {
        return name.split("_").joinToString(separator = " ") { it.lowercase().replaceFirstChar { it.uppercase() } }
    }
}

enum class TarotCard(
    val suite: Suites,
    @DrawableRes val img: Int,
    @DrawableRes val cover_img: Int = R.drawable.backside,
    val descriptionUpright: String? = null,
    val descriptionReversed: String? = null,
    val affiliationUpright: List<String>? = null,
    val affiliationReversed: List<String>? = null,
    var orientation: Int = 1,
) {
    The_Fool(suite = Suites.MAJOR, img = R.drawable.m00),
    The_Magician(suite = Suites.MAJOR, img = R.drawable.m01),
    The_High_Priestess(suite = Suites.MAJOR, img = R.drawable.m02),
    The_Empress(suite = Suites.MAJOR, img = R.drawable.m03),
    The_Emperor(suite = Suites.MAJOR, img = R.drawable.m04),
    The_Hierophant(suite = Suites.MAJOR, img = R.drawable.m05),
    The_Lovers(suite = Suites.MAJOR, img = R.drawable.m06),
    The_Chariot(suite = Suites.MAJOR, img = R.drawable.m07),
    Strength(suite = Suites.MAJOR, img = R.drawable.m08),
    The_Hermit(suite = Suites.MAJOR, img = R.drawable.m09),
    Wheel_of_Fortune(suite = Suites.MAJOR, img = R.drawable.m10),
    Justice(suite = Suites.MAJOR, img = R.drawable.m11),
    The_Hanged_Man(suite = Suites.MAJOR, img = R.drawable.m12),
    Death(suite = Suites.MAJOR, img = R.drawable.m13),
    Temperance(suite = Suites.MAJOR, img = R.drawable.m14),
    The_Devil(suite = Suites.MAJOR, img = R.drawable.m15),
    The_Tower(suite = Suites.MAJOR, img = R.drawable.m16),
    The_Star(suite = Suites.MAJOR, img = R.drawable.m17),
    The_Moon(suite = Suites.MAJOR, img = R.drawable.m18),
    The_Sun(suite = Suites.MAJOR, img = R.drawable.m19),
    Judgement(suite = Suites.MAJOR, img = R.drawable.m20),
    The_World(suite = Suites.MAJOR, img = R.drawable.m21),
    Ace_of_Wands(suite = Suites.WANDS, img = R.drawable.w01),
    Two_of_Wands(suite = Suites.WANDS, img = R.drawable.w02),
    Three_of_Wands(suite = Suites.WANDS, img = R.drawable.w03),
    Four_of_Wands(suite = Suites.WANDS, img = R.drawable.w04),
    Five_of_Wands(suite = Suites.WANDS, img = R.drawable.w05),
    Six_of_Wands(suite = Suites.WANDS, img = R.drawable.w06),
    Seven_of_Wands(suite = Suites.WANDS, img = R.drawable.w07),
    Eight_of_Wands(suite = Suites.WANDS, img = R.drawable.w08),
    Nine_of_Wands(suite = Suites.WANDS, img = R.drawable.w09),
    Ten_of_Wands(suite = Suites.WANDS, img = R.drawable.w10),
    Page_of_Wands(suite = Suites.WANDS, img = R.drawable.w11),
    Knight_of_Wands(suite = Suites.WANDS, img = R.drawable.w12),
    Queen_of_Wands(suite = Suites.WANDS, img = R.drawable.w13),
    King_of_Wands(suite = Suites.WANDS, img = R.drawable.w14),
    Ace_of_Cups(suite = Suites.CUPS, img = R.drawable.c01),
    Two_of_Cups(suite = Suites.CUPS, img = R.drawable.c02),
    Three_of_Cups(suite = Suites.CUPS, img = R.drawable.c03),
    Four_of_Cups(suite = Suites.CUPS, img = R.drawable.c04),
    Five_of_Cups(suite = Suites.CUPS, img = R.drawable.c05),
    Six_of_Cups(suite = Suites.CUPS, img = R.drawable.c06),
    Seven_of_Cups(suite = Suites.CUPS, img = R.drawable.c07),
    Eight_of_Cups(suite = Suites.CUPS, img = R.drawable.c08),
    Nine_of_Cups(suite = Suites.CUPS, img = R.drawable.c09),
    Ten_of_Cups(suite = Suites.CUPS, img = R.drawable.c10),
    Page_of_Cups(suite = Suites.CUPS, img = R.drawable.c11),
    Knight_of_Cups(suite = Suites.CUPS, img = R.drawable.c12),
    Queen_of_Cups(suite = Suites.CUPS, img = R.drawable.c13),
    King_of_Cups(suite = Suites.CUPS, img = R.drawable.c14),
    Ace_of_Swords(suite = Suites.SWORDS, img = R.drawable.s01),
    Two_of_Swords(suite = Suites.SWORDS, img = R.drawable.s02),
    Three_of_Swords(suite = Suites.SWORDS, img = R.drawable.s03),
    Four_of_Swords(suite = Suites.SWORDS, img = R.drawable.s04),
    Five_of_Swords(suite = Suites.SWORDS, img = R.drawable.s05),
    Six_of_Swords(suite = Suites.SWORDS, img = R.drawable.s06),
    Seven_of_Swords(suite = Suites.SWORDS, img = R.drawable.s07),
    Eight_of_Swords(suite = Suites.SWORDS, img = R.drawable.s08),
    Nine_of_Swords(suite = Suites.SWORDS, img = R.drawable.s09),
    Ten_of_Swords(suite = Suites.SWORDS, img = R.drawable.s10),
    Page_of_Swords(suite = Suites.SWORDS, img = R.drawable.s11),
    Knight_of_Swords(suite = Suites.SWORDS, img = R.drawable.s12),
    Queen_of_Swords(suite = Suites.SWORDS, img = R.drawable.s13),
    King_of_Swords(suite = Suites.SWORDS, img = R.drawable.s14),
    Ace_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p01),
    Two_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p02),
    Three_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p03),
    Four_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p04),
    Five_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p05),
    Six_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p06),
    Seven_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p07),
    Eight_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p08),
    Nine_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p09),
    Ten_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p10),
    Page_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p11),
    Knight_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p12),
    Queen_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p13),
    King_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p14);

    override fun toString(): String {
        return name.split("_").joinToString(separator = " ") { it.lowercase().replaceFirstChar { it.uppercase() } }
    }
}

data class Draw(
    val spread: Spread,
    val listOfCards: List<TarotCard>,
    val postback: () -> Unit = {}
) {
    var cardsFlipState = (1..spread.nCards).map { false }.toMutableList()
}

enum class Spread(
    @DrawableRes val schemeImg: Int,
    val description: String,
    val nCards: Int,
    val affiliation: SpreadAffiliation,
    val manaCost: Int,
    private val flipChance: Double = 0.38,
) {
    SINGLE_CARD(schemeImg = R.drawable.single_card, description = "Single Card", nCards = 1, affiliation = SpreadAffiliation.CLASSIC, manaCost = 3),
    THREE_CARDS(schemeImg = R.drawable.three_card, description = "Three Card", nCards = 3, affiliation = SpreadAffiliation.CLASSIC, manaCost = 4),
    BROKEN_HEART(schemeImg = R.drawable.broken_heart, description = "Broken heart", nCards = 7, affiliation = SpreadAffiliation.RELATIONSHIP, manaCost = 7),
    HEALING_HEARTS(schemeImg = R.drawable.healing_hearts, description = "Healing hearts", nCards = 6, affiliation = SpreadAffiliation.RELATIONSHIP, manaCost = 6),
    PYRAMID(schemeImg = R.drawable.pyramid, description = "Pyramid", nCards = 6, affiliation = SpreadAffiliation.RELATIONSHIP, manaCost = 6);

    override fun toString(): String {
        return name.split("_").joinToString(separator = " ") { it.lowercase().replaceFirstChar { it.uppercase() } }
    }

    fun draw(): List<TarotCard> {
        val drawn = TarotCard.entries.shuffled().take(nCards)
        return drawn.map {
            if (Math.random() < flipChance) {
                it.orientation = -1
                it
            } else {
                it
            }
        }
    }
}

enum class SpreadAffiliation {
    CLASSIC,
    RELATIONSHIP,
    CAREER
}