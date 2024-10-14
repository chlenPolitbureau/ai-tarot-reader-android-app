package com.tarotreader.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tarotreader.app.model.Prediction

@Composable
fun PredictionHistoryView(predictions: List<Prediction>) {
    if (predictions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("There's no predictions yet")}
    } else {
        LazyColumn {
            items(predictions.size) { index ->
                PredictionCard(predictions[index])
            }
        }
    }
}

@Composable
fun PredictionCard(prediction: Prediction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = prediction.question)
            Text(
                text= prediction.prediction,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            // ... other prediction details
        }
    }
}