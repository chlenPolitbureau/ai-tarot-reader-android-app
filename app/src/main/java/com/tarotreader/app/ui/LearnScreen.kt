package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tarotreader.app.R

@Preview
@Composable
fun ContentTabs() {
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
            0 -> CardsContent()
            1 -> SpreadsContent()
            2 -> ArticlesContent()
        }
    }
}

@Composable
fun CardsContent() {
    // Implement your Cards content here with LazyColumn and filtering chips
    val filters = listOf("Filter 1", "Filter 2", "Filter 3")
    var selectedFilter by remember { mutableStateOf("") }

    Column {
        LazyRow (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(filters) { filter -> FilterChip(
                onClick = { selectedFilter = filter },
                selected = selectedFilter == filter,
                label = { Text(filter) }
            )
            }
        }

        LazyVerticalGrid (
            columns = GridCells.Fixed(3)
        ) {
            // Display cards based on selectedFilter
            items(10) { index ->
                CardForGrid(
                    modifier = Modifier.padding(8.dp)
                )
                }
            }
        }
    }


@Composable
fun SpreadsContent() {
    // Implement your Spreads content here with LazyColumn
    LazyColumn {
        items(10) { index ->
            Card(modifier = Modifier.padding(8.dp)) {
                Text("Spread ${index + 1}")
            }
        }
    }
}

@Preview
@Composable
fun ArticlesContent() {
    // Implement your Articles content here with LazyColumn and image + headline layout
    LazyColumn {
        items(10) { index ->
            Row(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.queen_of_spades2),// Replace with your image
                    contentDescription = "Article Image",
                    modifier = Modifier.size(50.dp)
                )
                Text("Headline ${index + 1}", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Preview
@Composable
fun CardForGrid(
    cardName: String = "Card Name",
    @DrawableRes img: Int = R.drawable.c01,
    modifier: Modifier = Modifier
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            cardName,
            modifier = modifier
        )
        Image(
            painter = painterResource(id = R.drawable.c01),
            contentDescription = cardName
        )
    }
}