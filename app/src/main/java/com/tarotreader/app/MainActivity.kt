package com.tarotreader.app

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.model.ChatViewModelFactory
import com.tarotreader.app.model.Prediction
import com.tarotreader.app.ui.theme.TarotReaderTheme
import kotlinx.collections.immutable.mutate

val Context.dataStore by dataStore("settings.json", AppSettingsSerializer)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarotReaderTheme {
                val dataStore = dataStore
                val viewModelFactory = ChatViewModelFactory(dataStore)
                val chatViewModel = ViewModelProvider(this, viewModelFactory)[ChatViewModel::class.java]
                TarotReaderApp(
                    dataStore = dataStore,
                    chatViewModel = chatViewModel,
                    context = this
                )
            }
        }
    }

    private suspend fun setLanguage(language: Language) {
        dataStore.updateData {
            it.copy(
                language = language
            )
        }
    }

    private suspend fun addPrediction(prediction: Prediction) {
        dataStore.updateData {
            it.copy(
                predictions = it.predictions.mutate {
                    it.add(prediction)
                }
            )
        }
    }
}