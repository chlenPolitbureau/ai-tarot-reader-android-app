package com.tarotreader.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tarotreader.app.model.CardViewModel
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.MaterialCard
import com.tarotreader.app.model.SomeViewModel
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography

@Composable
fun MainScreen(
    navController: NavHostController,
    appViewModel: AppViewModel,
    cardViewModel: CardViewModel
) {
    val viewModel = viewModel<SomeViewModel>()

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = viewModel.backgroundColor
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {

            WelcomeMessage(
                username = appViewModel.currentUserName.collectAsState(initial = "Guest").value,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            RotatableCard(
                card = MaterialCard(
                    card = TarotCard.Page_of_Cups
                ),
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Button(
                    onClick = {
                        viewModel.changeBackgroundColor()
                    }
                ) {
                    Text(text = "Add Shuffle")
                }
            }
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