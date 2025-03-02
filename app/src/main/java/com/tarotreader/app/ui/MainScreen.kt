package com.tarotreader.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.DailyAdvice
import com.tarotreader.app.model.DailyAdviceCard
import com.tarotreader.app.ui.theme.Typography
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun MainScreen(
    appViewModel: AppViewModel,
    navController: NavHostController
) {
    val appDataStoreState = appViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val sessionLaunchTimeStampMillis =
            LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = true) {
            appViewModel.sessionLaunch(
                lastSessionMillis = appDataStoreState.value.lastSessionDateTimeMilliSec,
                nowMillis = sessionLaunchTimeStampMillis
            )
        }

        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {
            val adviceListWithNavController = appViewModel.dailyAdviceList
            adviceListWithNavController.map {
                it.navController = navController
            }

            WelcomeMessage(
                username = appDataStoreState.value.userName,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .fillMaxWidth()
            )

            DailyAdviceBlock(
                dailyAdvice = adviceListWithNavController
            )

            Row {
                ElevatedButton(onClick = {
                    scope.launch {
                        appViewModel.updateGender(
                            gender = "Male"
                        )
                    }
                }) {
                    Text(
                        "Become a man"
                    )
                }
                ElevatedButton(onClick = {
                    scope.launch {
                        appViewModel.updateGender(
                            "Female")
                    }
                }) {
                    Text("Become a woman")
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
                    appDataStoreState.value.dateOfBirth?.let {
                        Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC).toLocalDate()
                    }
                }")
            }
        }
    }
}

@Composable
fun WelcomeMessage(
    username: String = "Guest",
    modifier: Modifier = Modifier,
    textstyle: androidx.compose.ui.text.TextStyle = Typography.bodyMedium
) {
    val name = if (username == "") "Guest" else username
    Text(
        text = "Welcome, $name!",
        modifier = modifier,
        style = textstyle
    )
}

@Composable
fun DailyAdviceBlock(
    dailyAdvice: List<DailyAdvice>
) {
    Column {
        Text(
            text = "Daily Advice",
            style = Typography.bodyMedium
        )
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(dailyAdvice) {
                item -> DailyAdviceCard(
                dailyAdvice = item,
                modifier = Modifier.padding(
                    top = 5.dp,
                    end = 5.dp)
                )
            }
        }
    }
}