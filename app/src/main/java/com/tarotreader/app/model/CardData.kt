package com.tarotreader.app.model

import androidx.annotation.DrawableRes
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
    val descriptionUpright: Int,
    val descriptionReversed: String? = null,
    val affiliationUpright: List<String>? = null,
    val affiliationReversed: List<String>? = null,
    var orientation: Int = 1,
) {
    The_Fool(suite = Suites.MAJOR, img = R.drawable.m00,
        descriptionUpright = R.string.TheFoolDescription),
    The_Magician(suite = Suites.MAJOR, img = R.drawable.m01,
        descriptionUpright = R.string.TheMagicianDescription),
    The_High_Priestess(suite = Suites.MAJOR, img = R.drawable.m02,
        descriptionUpright = R.string.TheHighPriestessDescription),
    The_Empress(suite = Suites.MAJOR, img = R.drawable.m03,
        descriptionUpright = R.string.TheEmpressDescription),
    The_Emperor(suite = Suites.MAJOR, img = R.drawable.m04,
        descriptionUpright = R.string.TheEmperorDescription),
    The_Hierophant(suite = Suites.MAJOR, img = R.drawable.m05,
        descriptionUpright = R.string.TheHierophantDescription),
    The_Lovers(suite = Suites.MAJOR, img = R.drawable.m06,
        descriptionUpright = R.string.TheLoversDescription),
    The_Chariot(suite = Suites.MAJOR, img = R.drawable.m07,
        descriptionUpright = R.string.TheChariotDescription),
    Strength(suite = Suites.MAJOR, img = R.drawable.m08,
        descriptionUpright = R.string.StrengthDescription),
    The_Hermit(suite = Suites.MAJOR, img = R.drawable.m09,
        descriptionUpright = R.string.TheHermitDescription),
    Wheel_of_Fortune(suite = Suites.MAJOR, img = R.drawable.m10,
        descriptionUpright = R.string.WheelofFortuneDescription),
    Justice(suite = Suites.MAJOR, img = R.drawable.m11,
        descriptionUpright = R.string.JusticeDescription),
    The_Hanged_Man(suite = Suites.MAJOR, img = R.drawable.m12,
        descriptionUpright = R.string.TheHangedManDescription),
    Death(suite = Suites.MAJOR, img = R.drawable.m13,
        descriptionUpright = R.string.DeathDescription),
    Temperance(suite = Suites.MAJOR, img = R.drawable.m14,
        descriptionUpright = R.string.TemperanceDescription),
    The_Devil(suite = Suites.MAJOR, img = R.drawable.m15,
        descriptionUpright = R.string.TheDevilDescription),
    The_Tower(suite = Suites.MAJOR, img = R.drawable.m16,
        descriptionUpright = R.string.TheTowerDescription),
    The_Star(suite = Suites.MAJOR, img = R.drawable.m17,
        descriptionUpright = R.string.TheStarDescription),
    The_Moon(suite = Suites.MAJOR, img = R.drawable.m18,
        descriptionUpright = R.string.TheMoonDescription),
    The_Sun(suite = Suites.MAJOR, img = R.drawable.m19,
        descriptionUpright = R.string.TheSunDescription),
    Judgement(suite = Suites.MAJOR, img = R.drawable.m20,
        descriptionUpright = R.string.JudgementDescription),
    The_World(suite = Suites.MAJOR, img = R.drawable.m21,
        descriptionUpright = R.string.TheWorldDescription),
    Ace_of_Wands(suite = Suites.WANDS, img = R.drawable.w01,
        descriptionUpright = R.string.AceofWandsDescription),
    Two_of_Wands(suite = Suites.WANDS, img = R.drawable.w02,
        descriptionUpright = R.string.TwoofWandsDescription),
    Three_of_Wands(suite = Suites.WANDS, img = R.drawable.w03,
        descriptionUpright = R.string.ThreeofWandsDescription),
    Four_of_Wands(suite = Suites.WANDS, img = R.drawable.w04,
        descriptionUpright = R.string.FourofWandsDescription),
    Five_of_Wands(suite = Suites.WANDS, img = R.drawable.w05,
        descriptionUpright = R.string.FiveofWandsDescription),
    Six_of_Wands(suite = Suites.WANDS, img = R.drawable.w06,
        descriptionUpright = R.string.SixofWandsDescription),
    Seven_of_Wands(suite = Suites.WANDS, img = R.drawable.w07,
        descriptionUpright = R.string.SevenofWandsDescription),
    Eight_of_Wands(suite = Suites.WANDS, img = R.drawable.w08,
        descriptionUpright = R.string.EightofWandsDescription),
    Nine_of_Wands(suite = Suites.WANDS, img = R.drawable.w09,
        descriptionUpright = R.string.NineofWandsDescription),
    Ten_of_Wands(suite = Suites.WANDS, img = R.drawable.w10,
        descriptionUpright = R.string.TenofWandsDescription),
    Page_of_Wands(suite = Suites.WANDS, img = R.drawable.w11,
        descriptionUpright = R.string.PageofWandsDescription),
    Knight_of_Wands(suite = Suites.WANDS, img = R.drawable.w12,
        descriptionUpright = R.string.KnightofWandsDescription),
    Queen_of_Wands(suite = Suites.WANDS, img = R.drawable.w13,
        descriptionUpright = R.string.QueenofWandsDescription),
    King_of_Wands(suite = Suites.WANDS, img = R.drawable.w14,
        descriptionUpright = R.string.KingofWandsDescription),
    Ace_of_Cups(suite = Suites.CUPS, img = R.drawable.c01,
        descriptionUpright = R.string.AceofCupsDescription),
    Two_of_Cups(suite = Suites.CUPS, img = R.drawable.c02,
        descriptionUpright = R.string.TwoofCupsDescription),
    Three_of_Cups(suite = Suites.CUPS, img = R.drawable.c03,
        descriptionUpright = R.string.ThreeofCupsDescription),
    Four_of_Cups(suite = Suites.CUPS, img = R.drawable.c04,
        descriptionUpright = R.string.FourofCupsDescription),
    Five_of_Cups(suite = Suites.CUPS, img = R.drawable.c05,
        descriptionUpright = R.string.FiveofCupsDescription),
    Six_of_Cups(suite = Suites.CUPS, img = R.drawable.c06,
        descriptionUpright = R.string.SixofCupsDescription),
    Seven_of_Cups(suite = Suites.CUPS, img = R.drawable.c07,
        descriptionUpright = R.string.SevenofCupsDescription),
    Eight_of_Cups(suite = Suites.CUPS, img = R.drawable.c08,
        descriptionUpright = R.string.EightofCupsDescription),
    Nine_of_Cups(suite = Suites.CUPS, img = R.drawable.c09,
        descriptionUpright = R.string.NineofCupsDescription),
    Ten_of_Cups(suite = Suites.CUPS, img = R.drawable.c10,
        descriptionUpright = R.string.TenofCupsDescription),
    Page_of_Cups(suite = Suites.CUPS, img = R.drawable.c11,
        descriptionUpright = R.string.PageofCupsDescription),
    Knight_of_Cups(suite = Suites.CUPS, img = R.drawable.c12,
        descriptionUpright = R.string.KnightofCupsDescription),
    Queen_of_Cups(suite = Suites.CUPS, img = R.drawable.c13,
        descriptionUpright = R.string.QueenofCupsDescription),
    King_of_Cups(suite = Suites.CUPS, img = R.drawable.c14,
        descriptionUpright = R.string.KingofCupsDescription),
    Ace_of_Swords(suite = Suites.SWORDS, img = R.drawable.s01,
        descriptionUpright = R.string.AceofSwordsDescription),
    Two_of_Swords(suite = Suites.SWORDS, img = R.drawable.s02,
        descriptionUpright = R.string.TwoofSwordsDescription),
    Three_of_Swords(suite = Suites.SWORDS, img = R.drawable.s03,
        descriptionUpright = R.string.ThreeofSwordsDescription),
    Four_of_Swords(suite = Suites.SWORDS, img = R.drawable.s04,
        descriptionUpright = R.string.FourofSwordsDescription),
    Five_of_Swords(suite = Suites.SWORDS, img = R.drawable.s05,
        descriptionUpright = R.string.FiveofSwordsDescription),
    Six_of_Swords(suite = Suites.SWORDS, img = R.drawable.s06,
        descriptionUpright = R.string.SixofSwordsDescription),
    Seven_of_Swords(suite = Suites.SWORDS, img = R.drawable.s07,
        descriptionUpright = R.string.SevenofSwordsDescription),
    Eight_of_Swords(suite = Suites.SWORDS, img = R.drawable.s08,
        descriptionUpright = R.string.EightofSwordsDescription),
    Nine_of_Swords(suite = Suites.SWORDS, img = R.drawable.s09,
        descriptionUpright = R.string.NineofSwordsDescription),
    Ten_of_Swords(suite = Suites.SWORDS, img = R.drawable.s10,
        descriptionUpright = R.string.TenofSwordsDescription),
    Page_of_Swords(suite = Suites.SWORDS, img = R.drawable.s11,
        descriptionUpright = R.string.PageofSwordsDescription),
    Knight_of_Swords(suite = Suites.SWORDS, img = R.drawable.s12,
        descriptionUpright = R.string.KnightofSwordsDescription),
    Queen_of_Swords(suite = Suites.SWORDS, img = R.drawable.s13,
        descriptionUpright = R.string.QueenofSwordsDescription),
    King_of_Swords(suite = Suites.SWORDS, img = R.drawable.s14,
        descriptionUpright = R.string.KingofSwordsDescription),
    Ace_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p01,
        descriptionUpright = R.string.AceofPentaclesDescription),
    Two_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p02,
        descriptionUpright = R.string.TwoofPentaclesDescription),
    Three_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p03,
        descriptionUpright = R.string.ThreeofPentaclesDescription),
    Four_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p04,
        descriptionUpright = R.string.FourofPentaclesDescription),
    Five_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p05,
        descriptionUpright = R.string.FiveofPentaclesDescription),
    Six_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p06,
        descriptionUpright = R.string.SixofPentaclesDescription),
    Seven_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p07,
        descriptionUpright = R.string.SevenofPentaclesDescription),
    Eight_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p08,
        descriptionUpright = R.string.EightofPentaclesDescription),
    Nine_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p09,
        descriptionUpright = R.string.NineofPentaclesDescription),
    Ten_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p10,
        descriptionUpright = R.string.TenofPentaclesDescription),
    Page_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p11,
        descriptionUpright = R.string.PageofPentaclesDescription),
    Knight_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p12,
        descriptionUpright = R.string.KnightofPentaclesDescription),
    Queen_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p13,
        descriptionUpright = R.string.QueenofPentaclesDescription),
    King_of_Pentacles(suite = Suites.PENTACLES, img = R.drawable.p14,
        descriptionUpright = R.string.KingofPentaclesDescription);

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
    val shortDescription: String,
    val nCards: Int,
    val affiliation: SpreadAffiliation,
    val manaCost: Int,
    private val flipChance: Double = 0.38,
) {
    SINGLE_CARD(
        schemeImg = R.drawable.single_card,
        shortDescription = "Quick spread for Yes or No queries",
        nCards = 1,
        affiliation = SpreadAffiliation.GENERIC,
        manaCost = 3
    ),
    THREE_CARDS(
        schemeImg = R.drawable.three_card,
        shortDescription = "Classic spread to find out the Past, the Future and the Present",
        nCards = 3, affiliation = SpreadAffiliation.GENERIC, manaCost = 4),
    BROKEN_HEART(
        schemeImg = R.drawable.broken_heart,
        shortDescription = "Specialized spread for relationship crisis",
        nCards = 7, affiliation = SpreadAffiliation.LOVE, manaCost = 7),
    HEALING_HEARTS(
        schemeImg = R.drawable.healing_hearts,
        shortDescription = "Get a compassionate overview ones your love affairs",
        nCards = 6, affiliation = SpreadAffiliation.LOVE, manaCost = 6),
    PYRAMID(
        schemeImg = R.drawable.pyramid,
        shortDescription = "Unravel complex situations with this spread",
        nCards = 6, affiliation = SpreadAffiliation.CAREER, manaCost = 6);

    fun toReadableString(): String {
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
    GENERIC,
    CAREER,
    LOVE;

    override fun toString(): String {
        return name.split("_").joinToString(separator = " ") { it.lowercase().replaceFirstChar { it.uppercase() } }
    }
}