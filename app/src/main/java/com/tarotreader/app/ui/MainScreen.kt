package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tarotreader.app.Chat
import com.tarotreader.app.R
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.DailyAdvice
import com.tarotreader.app.model.DailyAdviceCard
import com.tarotreader.app.model.Prediction
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography
import com.tarotreader.app.ui.theme.bkgrndGrey
import com.tarotreader.app.ui.theme.poshGreen
import kotlinx.collections.immutable.PersistentList
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun MainScreen(
    appViewModel: AppViewModel,
    navController: NavHostController,
) {
    val appDataStoreState = appViewModel.uiState.collectAsState()
    val predictions = appDataStoreState.value.predictions
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val sessionLaunchTimeStampMillis =
            LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
        val isFirstLaunch = appViewModel.isFirstLaunch.collectAsState()

        LaunchedEffect(key1 = true) {
            // use lifecycle scope to wait until it's completed
            // move all calculation to the viewmodel
            appViewModel.sessionLaunch(
                lastSessionMillis = appDataStoreState.value.lastSessionDateTimeMilliSec,
                nowMillis = sessionLaunchTimeStampMillis
            )
            if (isFirstLaunch.value) {
                appViewModel.updateFirstLaunch(false)
            }
        }

        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            WelcomeMessage(
                username = appDataStoreState.value.userName,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .fillMaxWidth()
            )

            MainPathBlock(
                navController = navController
            )

            DailyAdviceBlock(
                dailyAdvice = appViewModel.dailyAdviceList,
                navController = navController
            )

            StatsBlock(
                predictionsList = predictions
            )

            BannerAd(
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                adId = "ca-app-pub-3940256099942544/9214589741"
            )
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
    dailyAdvice: List<DailyAdvice>,
    navController: NavHostController,
    modifier: Modifier = Modifier.padding(
        top=40.dp
    )
) {
    Column (
        modifier = modifier
    ) {
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
                navController = navController,
                modifier = Modifier.padding(
                    top = 5.dp,
                    end = 5.dp)
                )
            }
        }
    }
}

@Composable
fun MainPathBlock(
    navController: NavHostController,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        PathBox(
            image = R.drawable.yes_or_no,
            headline = "Yes or No",
            route = Chat(
                spread = Spread.SINGLE_CARD
            ),
            navController = navController
        )

        PathBox(
            image = R.drawable.daily_inspiration,
            headline = "Daily Inspiration",
            route = Chat(
                spread = Spread.THREE_CARDS
            ),
            navController = navController
        )
    }
}

@Composable
fun PathBox(
    @DrawableRes image: Int,
    headline: String,
    route: kotlin.Any,
    backgroundColor: Color = bkgrndGrey,
    navController: NavHostController,
    modifier: Modifier = Modifier
        .height(320.dp)
        .width(210.dp)
        .fillMaxWidth()
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier.
                            clickable {
                                navController.navigate(route)
                            }
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .height(60.dp)
        ) {
            Text(
                text = headline,
                style = Typography.titleMedium,
                modifier = Modifier.padding(
                    10.dp
                )
            )
        }
    }
}

@Composable
fun StatsBlock(
    predictionsList: PersistentList<Prediction>,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val cards = mutableListOf<TarotCard>()
    predictionsList.forEach {
        cards.addAll(it.cards)
    }
    val counts = cards.groupingBy { it }
        .eachCount()
    val maxCount = counts.values.maxOrNull() ?: 0
    val favouriteCard = if (counts.isNotEmpty()) counts.filterValues { it == maxCount }.keys.first() else
        TarotCard.Temperance

    fun countDifferencesLessThanFixed(sortedList: List<Long>, fixedInt: Int = 86400000): Int {
        var count = if(sortedList.isEmpty()) 0 else 1
        var listOfDates: List<LocalDateTime> = if(count > 0) mutableListOf(
            Instant.ofEpochMilli(sortedList[0]).atOffset(ZoneOffset.UTC).toLocalDate().atStartOfDay()
        ) else mutableListOf()

        // Iterate through the sorted list and calculate the differences
        for (i in 0 until sortedList.size - 1) {
            val difference = sortedList[i + 1] - sortedList[i]
            if (difference < fixedInt) {
                count++
                listOfDates = listOfDates.plus(
                    Instant.ofEpochMilli(sortedList[i + 1]).atOffset(ZoneOffset.UTC).toLocalDate().atStartOfDay()
                )
            }
        }
        return listOfDates.distinct().size
    }

    val predictionTimeStampList = mutableListOf<Long>()
    predictionsList.forEach {
        predictionTimeStampList.add(it.dateTime)
    }
    val sortedList = predictionTimeStampList.sorted()
    println(sortedList)
    val streak = countDifferencesLessThanFixed(
        sortedList
    )

    Card(
        modifier = Modifier.padding(top = 40.dp),
        border = BorderStroke(3.dp, poshGreen),
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        )
    ) {
        Column(
            modifier = modifier
        ) {
            Row (
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 15.dp)
            ) {
                Text(
                    text = "Your stats",
                    style = Typography.bodyMedium
                )
            }
            
            if(predictionsList.size > 0) {
                StatsPlaceholder(
                    icon = R.drawable.num_predictions,
                    text = "Total predictions",
                    value = predictionsList.size
                )
                StatsPlaceholder(
                    icon = R.drawable.popular_tarot_card,
                    text = "Favourite card",
                    value = favouriteCard.toString(),
                    extraValue = maxCount
                )
                StatsPlaceholder(
                    icon = R.drawable.streak,
                    text = "Longest streak, days",
                    value = streak
                )
            } else {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.start_reading),
                        contentDescription = "Start reading",
                        modifier = Modifier.size(130.dp)
                    )
                    Text(
                        "Start reading to show your stats"
                    )
                }
            }
        }
    }
}

@Composable
fun StatsPlaceholder(
    @DrawableRes icon: Int,
    text: String,
    value: kotlin.Any,
    extraValue: Int? = null
) {

    val extraText = if (extraValue != null) " ($extraValue)" else ""
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 5.dp, top = 5.dp, end = 10.dp, bottom = 10.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Total predictions",
            tint = Color.Unspecified,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text,
            modifier = Modifier.padding(start = 10.dp),
            style = Typography.bodyLarge
        )
        Text(
            "$value"  + "$extraText",
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp)
                .fillMaxWidth()
                .widthIn(max = 250.dp),
            style = Typography.bodyLarge,
            textAlign = TextAlign.End,
            maxLines = 2
        )
    }
}

@Composable
fun BannerAd(
    modifier: Modifier, adId: String
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = {
            context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}