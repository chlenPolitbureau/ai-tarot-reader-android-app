package com.tarotreader.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.Content
import com.tarotreader.app.data.ArticlesDataSource
import com.tarotreader.app.model.Article
import com.tarotreader.app.model.LearningViewModel
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography


@Composable
fun ContentTabs(
    learningViewModel: LearningViewModel = LearningViewModel(),
    navController: NavHostController
) {
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Cards", "Spreads", "Articles")

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(selectedTabIndex = selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text ={ Text(title) }
                )
            }
        }

        when (selectedTab) {
            0 -> CardsContent(
                learningViewModel = learningViewModel,
                navController = navController
            )
            1 -> SpreadsContent(
                learningViewModel = learningViewModel
            )
            2 -> ArticlesContent(
                dataSource = ArticlesDataSource,
                navController = navController
            )
        }
    }
}

@Composable
fun CardsContent(
    learningViewModel: LearningViewModel,
    navController: NavHostController
) {
    // Implement your Cards content here with LazyColumn and filtering chips
    val filters = learningViewModel.suites
    var selectedFilter by remember { mutableStateOf(filters) }
    var selectedChip by remember { mutableStateOf("") }
    val cards = learningViewModel.filteredCards(selectedFilter.toList())

    Column {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items(filters) { filter ->
                FilterChip(
                    onClick = { if(selectedChip == filter.toString()) {
                                    selectedChip = ""
                                    selectedFilter = filters
                                } else {
                                    selectedChip = filter.toString()
                                    selectedFilter = listOf(filter)
                                }
                              },
                    selected = selectedChip == filter.toString(),
                    label = { Text(filter.toString()) }
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(cards.size) { index ->
                CardForGrid(
                    card = cards[index],
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        navController.navigate(
                            Content(
                            type = "card",
                            id = cards[index].name
                            )
                        )
                    }
                )
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpreadsContent(
    modifier: Modifier = Modifier,
    learningViewModel: LearningViewModel = LearningViewModel()
)
{
    val spreadList = learningViewModel.spreads
    val affiliations = learningViewModel.spreadAffiliations
    // Implement your Spreads content here with LazyColumn
    LazyColumn {
        items(affiliations.size) { index ->
            Text(
                "Spreads for " + affiliations[index].name,
                style = Typography.titleSmall,
                modifier = Modifier.padding(8.dp)
                )
            LazyRow(
                modifier = modifier.fillMaxWidth()
            ) {
                val spreadListFiltered = spreadList.filter { it.affiliation == affiliations[index] }

                items(spreadListFiltered.size) { ind ->
                    SpreadCardComposable(
                        spread = spreadListFiltered[ind],
                        onClick = {},
                        modifier = Modifier.padding(end = 18.dp, bottom = 40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ArticlesContent(
    dataSource: ArticlesDataSource = ArticlesDataSource,
    navController: NavHostController
) {
    // Implement your Articles content here with LazyColumn and image + headline layout
    LazyColumn {
        items(dataSource.articles.size) { index ->
            ArticlePreviewCard(
                article = dataSource.articles[index],
                onClick = {}
            )
        }
    }
}


@Composable
fun CardForGrid(
    card: TarotCard,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            card.toString(),
            modifier = modifier,
            textAlign = TextAlign.Center,
            minLines = 2
        )
        Image(
            painter = painterResource(id = card.img),
            contentDescription = card.toString(),
            modifier = Modifier.clickable(
                onClick = onClick
            )
        )
    }
}

@Composable
fun SpreadCardComposable(
    modifier: Modifier = Modifier,
    spread: Spread,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = spread.schemeImg),
                contentDescription = spread.name,
                modifier = Modifier
                    .widthIn(min = 280.dp, max = 360.dp)
                    .height(150.dp)
                    .padding(top=6.dp)
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = spread.name)
                Text(text = spread.description)
                Button(
                    onClick = onClick,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Learn more")
                }
            }
        }
    }}

@Preview
@Composable
fun CardComposablePreview() {
    SpreadCardComposable(
        spread = Spread.SINGLE_CARD,
        onClick = {}
    )
}

@Composable
fun ArticlePreviewCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(article.img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(0.28f) // Image takes 1/4 of the row's width
                .aspectRatio(1f) // Maintain aspect ratio

        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(0.75f) // Text and button take 3/4 of the row's width
        ) {
            Text(
                text = article.header,
                style = Typography.titleSmall
            )
            Text(text = article.previewText)
            Button(onClick = onClick) {
                Text(text = "Read All")
            }
        }
    }
}

@Preview
@Composable
fun ArticlePreview() {
    val article = ArticlesDataSource.articles
    ArticlePreviewCard(
        article = article[0],
        onClick = {}
    )
}