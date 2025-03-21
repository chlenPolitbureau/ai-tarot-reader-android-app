package com.tarotreader.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.Chat
import com.tarotreader.app.R
import com.tarotreader.app.data.ArticlesDataSource
import com.tarotreader.app.model.Article
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography

@Composable
fun ContentViewPage(
    title: String = "Content",
    type: String?,
    id: String?,
    postback: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    navController: NavHostController? = null
) {
    Column {
    when(type) {
        "card" -> {
            postback("Card")
            CardDescriptionViewPage(cardName = id ?: "", navController = navController!!)
        }
        "spread" -> {
            postback("Spread")
            SpreadDescriptionViewPage(
                spread = Spread.valueOf(id ?: ""),
                navController = navController!!
            )
        }
        "article" -> {
            postback("Article")
            ArticleViewPage(article = ArticlesDataSource.articles.find { it.id == id }!!)
        }
        else -> {
        }
    }
    }
}

@Composable
fun ArticleViewPage(
    article: Article,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = article.img),
            contentDescription = article.header,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = article.header,
            style = Typography.titleLarge,
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.fullText,
            style = Typography.bodyMedium,
            modifier = Modifier
                .padding(5.dp)
                .verticalScroll(rememberScrollState())
        )
        }
    }

@Composable
fun CardDescriptionViewPage(
    cardName: String,
    navController: NavHostController
) {
    val card = TarotCard.entries.find { it.name == cardName }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (card != null) {
                Box(
                    modifier = Modifier
                        .height(260.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.background_horizontal),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        alpha = 0.33f
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = card.img),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(250.dp)
                                .padding(10.dp)
                        )
                    }
                }
                Text(
                    text = card.toString(),
                    style = Typography.titleSmall,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = card.descriptionUpright),
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                )
        }
    }
}

@Composable
fun SpreadDescriptionViewPage(
    spread: Spread,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .height(260.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_horizontal),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.33f
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = spread.schemeImg),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .padding(10.dp)
            )
        }
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpreadDescriptionRow(spread = spread)

        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = spread.toReadableString(),
                style = Typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
        Row (
            modifier = Modifier
                .padding(
                    start = 15.dp, end = 15.dp, top = 25.dp, bottom = 10.dp
                )
                .fillMaxWidth(),
        ) {
            Text(
                text = spread.shortDescription,
                style = Typography.bodySmall,
                textAlign = TextAlign.Justify
            )
        }
        ElevatedButton(
            onClick = {
                navController.navigate(
                    Chat(
                        spread = spread
                    )
                )
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Try it out",
                style = Typography.bodySmall
            )
        }
    }
}

@Composable
fun SpreadDescriptionRow(
    spread: Spread
) {
    Row (
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Mana cost",
                style = Typography.bodySmall
            )
            Text(
                spread.manaCost.toString(),
                modifier = Modifier.padding(10.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Number of cards",
                style = Typography.bodySmall
            )
            Text(
                spread.nCards.toString(),
                modifier = Modifier.padding(10.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Affinity",
                style = Typography.bodySmall
            )
            Text(
                spread.affiliation.name,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
fun SpreadDescriptionViewPagePreview() {
    SpreadDescriptionRow(spread = Spread.THREE_CARDS)
}