package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.R
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.model.Draw
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    title: String = "Main",
    navController: NavHostController,
    chatViewModel: ChatViewModel
) {
    Column(
        modifier = Modifier
            .padding(6.dp)
    ) {
        val manaPoints = chatViewModel.currentManaPoints.collectAsState(initial = 0).value
        val scope = rememberCoroutineScope()

        WelcomeMessage(
            username = chatViewModel.currentUserName.collectAsState(initial = "Guest").value,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        
        Text("Current mana balance: $manaPoints")
        
        Button(
            onClick = {
                scope.launch {
                    chatViewModel.updateMana(manaPoints, 2)
                }
            }
        ) {
            Text(text = "Deduct mana")
        }
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