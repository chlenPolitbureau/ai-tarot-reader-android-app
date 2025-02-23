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
import com.tarotreader.app.AppSettings
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.CurrencyType
import com.tarotreader.app.model.SomeViewModel
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun MainScreen(
    appViewModel: AppViewModel,
) {
    val viewModel = viewModel<SomeViewModel>()
    val appDataStoreState = appViewModel.uiState.collectAsState()

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = viewModel.backgroundColor
    ) {
        var r by remember { mutableStateOf(false) }
        val currencies = appDataStoreState.value.currencies
        val userName = appDataStoreState.value.userName
        val sessionLaunchTimeStampMillis = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()


        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {

            WelcomeMessage(
                username = userName,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            RotatableCard(
                card = TarotCard.The_Fool, rotatedState = r, flip = { r = !r })

            Row {
                Text("Add statistics?")
            }

            Row {
                Column {
                    currencies.forEach {
                        currency ->
                        Text("${currency.type}: ${currency.amount}")
                    }
                    Button(
                        onClick = {
                            appViewModel.updateCurrency(
                                type = CurrencyType.MANA,
                                amount = 10
                            )
                        }
                    ) {
                        Text(text = "Add Mana")
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "DTS ${appDataStoreState.value.lastSessionDateTimeMilliSec}")
            }

            Row {
                Text(text = "NTS ${sessionLaunchTimeStampMillis}")
            }

            Row {
                Text(text = "Gender: ${appDataStoreState.value.gender}")
            }
            Row {
                Text(text = "Date of Birth: ${
                    appDataStoreState.value.dateOfBirth?.let { Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC).toLocalDate() }
                }")
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
    val name = if (username == "") "Guest" else username
    Text(
        text = "Welcome, $name!",
        modifier = modifier,
        style = textstyle
    )
}