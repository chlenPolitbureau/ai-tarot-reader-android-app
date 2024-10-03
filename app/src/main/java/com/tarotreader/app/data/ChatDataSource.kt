package com.tarotreader.app.data

import androidx.compose.material3.Text
import com.tarotreader.app.model.Author
import com.tarotreader.app.model.ChatMessage
import com.tarotreader.app.model.ChatMessageOption

object ChatDataSource {
    val initState =
        ChatMessage(
            text = "Greetings fortune seeker! I'm a Tarot Reader. You can ask me about anything you'd" +
                    " like to know. I'll make a spread and read it. So please ask. Or choose from the options below",
            author = Author("app"),
            id = 0,
            options = listOf(
                ChatMessageOption(
                    onClick = { },
                    content = { Text("Option 1") }
                ),
                ChatMessageOption(
                    onClick = { },
                    content = { Text("Option 2") }
                )
            )
        )
}