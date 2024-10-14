package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography

@Composable
fun MainScreen(
    title: String = "Main",
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(6.dp)
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