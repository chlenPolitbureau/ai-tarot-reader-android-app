package com.tarotreader.app.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import com.tarotreader.app.model.Prediction
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PredictionHistoryView(predictions: List<Prediction>) {

    val reversed = predictions.reversed()

    Column {
        if (reversed.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("There's no predictions yet")
            }
        } else {
                LazyColumn {
                    items(reversed.size) { index ->
                        val thisDate = LocalDate.parse(
                            reversed[index].dateTime.substring(
                                range = 0..9
                            )
                        )
                        val prevDate = LocalDate.parse(
                            reversed[if(index == 0) 0 else index - 1].dateTime.substring(
                                range = 0..9
                            )
                        )
                        if (thisDate != prevDate || index == 0) {
                            Text(
                                thisDate.format(DateTimeFormatter.ofPattern("MMMM dd, y")),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        PredictionCard(reversed[index])
                    }
                }
            }
        }
    }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PredictionCard(
    prediction: Prediction
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {

            }
    ) {
        val formatter = DateTimeFormatter.ofPattern("H:mm")
        Column(modifier = Modifier.padding(8.dp)) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = LocalDateTime.parse(prediction.dateTime).format(formatter)
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
                    text= "Spread: ${prediction.spread.toString()}"
                )
            }
            Row {
                Text(
                    text= prediction.prediction,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun PredictionBottomDrawer(
    prediction: Prediction
) {

}