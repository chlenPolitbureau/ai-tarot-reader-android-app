package com.tarotreader.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.ui.theme.TarotReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarotReaderTheme {
                val chatViewModel = viewModel<ChatViewModel>()
                TarotReaderApp(
                    chatViewModel = chatViewModel,
                    context = this
                )
            }
        }
    }
}