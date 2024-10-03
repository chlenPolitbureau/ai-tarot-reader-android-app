package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.tarotreader.app.data.BottomAppBarDataSource
import com.tarotreader.app.model.BottomBarMenuItem
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.ShuffleDeck
import com.tarotreader.app.ui.theme.Typography
import java.time.format.TextStyle

@Composable
fun MainScreen(
    navController: NavHostController,
) {
    BottomAppBarExample(
        navController = navController
    )
}

@Composable
fun BottomAppBarExample(
    items: List<BottomBarMenuItem> = BottomAppBarDataSource.items,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            Text("Top app bar")
        },
        bottomBar = {
            LazyRow (
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items.size) {
                        index ->
                    BottomAppBarItem(
                        text = items[index].title,
                        icon = items[index].icon,
                        onClick = { navController.navigate(items[index].route) })
                }
//            BottomAppBar(
//                actions = {
//                    LazyRow (
//                        horizontalArrangement = Arrangement.SpaceAround,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        items(items.size) {
//                            index ->
//                            BottomAppBarItem(
//                                text = items[index].title,
//                                icon = items[index].icon,
//                                onClick = items[index].onClick)
//                        }
//                    }
//                },
//            )
        }}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            WelcomeMessage()

            ShuffleDeck(
                imageList = listOf(
                    TarotCard.Knight_of_Wands,
                    TarotCard.Queen_of_Wands,
                    TarotCard.Six_of_Cups
                )
            )
        }
    }
}

@Composable
fun BottomAppBarItem(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        IconButton(
            onClick = onClick
            ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = text.toString(),
                modifier = Modifier
                    .clip(RectangleShape)
                    .padding(4.dp)
            )
        }
        Text(stringResource(text))
    }
}

@Composable
fun WelcomeMessage(
    username: String = "Guest",
    modifier: Modifier = Modifier,
    textstyle: androidx.compose.ui.text.TextStyle = Typography.bodyLarge
) {
    Text(
        text = "Welcome, $username!",
        modifier = modifier,
        style = textstyle
    )

}