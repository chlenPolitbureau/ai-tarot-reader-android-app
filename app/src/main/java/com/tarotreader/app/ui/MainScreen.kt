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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.SomeViewModel
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography

@Composable
fun MainScreen(
    navController: NavHostController,
    appViewModel: AppViewModel,
) {
    val viewModel = viewModel<SomeViewModel>()

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = viewModel.backgroundColor
    ) {

        val someViewModel = viewModel<SomeViewModel>()
        val otherViewModel = viewModel<SomeViewModel>()

        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {

            WelcomeMessage(
                username = appViewModel.currentUserName.collectAsState(initial = "Guest").value,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            RCard(
                card = TarotCard.The_Fool,
                n = 0,
                rotatedState = someViewModel.cardState[0],
                flip = someViewModel::flipOneCard
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