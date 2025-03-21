package com.tarotreader.app.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import com.tarotreader.app.model.Prediction
import com.tarotreader.app.ui.theme.Typography
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PredictionHistoryView(predictions: List<Prediction>) {

    val reversed = predictions.reversed()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val predictionToShow = remember { mutableStateOf<Prediction?>(null) }

    fun showBottomSheet(prediction: Prediction) {
        showBottomSheet = true
        predictionToShow.value = prediction
    }

    Column {
        if (reversed.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("There's no predictions yet")
            }
        } else {
                LazyColumn {
                    items(reversed.size) { index ->
                        val thisDate = Instant.ofEpochMilli(reversed[index].dateTime).atOffset(ZoneOffset.UTC).toLocalDate()
                        val prevDate = Instant.ofEpochMilli(reversed[if(index == 0) 0 else index - 1].dateTime).atOffset(ZoneOffset.UTC).toLocalDate()
                        if (thisDate != prevDate || index == 0) {
                            Text(
                                thisDate.format(DateTimeFormatter.ofPattern("MMMM dd, y")),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        PredictionCard(
                            prediction = reversed[index],
                            modifier = Modifier.clickable {
                                showBottomSheet(reversed[index])
                            }
                        )
                    }
                }
                if (showBottomSheet) {
                    ModalBottomSheet(onDismissRequest = {
                        showBottomSheet = false
                    }, sheetState = sheetState) {
                        predictionToShow.value?.let {
                            val scrollState = rememberScrollState()
                            val formatter = DateTimeFormatter.ofPattern("MMMM dd, y, H:mm")

                            Column (
                              modifier = Modifier.verticalScroll(
                                  state = scrollState
                              )
                            ) {
                                Text(
                                    text = "Prediction setup",
                                    style = Typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                        .fillMaxWidth()
                                )

                                Text(
                                    text =  "Spread: ${it.spread.toReadableString()} \n" +
                                            "Date: ${Instant.ofEpochMilli(it.dateTime).atOffset(ZoneOffset.UTC).format(formatter)} \n" +
                                            "Cards drawn: ${it.cards.joinToString()}"
                                )

                                Text(
                                    text = "Question",
                                    style = Typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                        .fillMaxWidth()
                                )

                                Text(
                                    text = it.question,
                                )

                                Text(
                                    text = "Reading",
                                    style = Typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 20.dp, bottom = 5.dp)
                                        .fillMaxWidth()
                                )
                                Text(
                                    text = it.prediction
                                )
                            }
                        }
                    }
                }
            }
        }
    }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PredictionCard(
    prediction: Prediction,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val formatter = DateTimeFormatter.ofPattern("H:mm")
        Column(modifier = Modifier.padding(8.dp)) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = Instant.ofEpochMilli(prediction.dateTime).atOffset(ZoneOffset.UTC).format(formatter)
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = prediction.question,
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(
                    text= "Spread: ${prediction.spread.toReadableString()}"
                )
            }

        }
    }
}