package com.tarotreader.app.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarotreader.app.data.ChatDataSource
import com.tarotreader.app.data.PredictRequest
import com.tarotreader.app.data.RetrofitClient
import kotlinx.coroutines.launch


class ChatViewModel: ViewModel() {
    private val _messages = mutableStateListOf(ChatDataSource.initState)
    val messages = _messages

    val ifExpectingPrediction: MutableState<Boolean> = mutableStateOf(false)
    val question: MutableState<String> = mutableStateOf("")
    val showChips: MutableState<Boolean> = mutableStateOf(true)

    fun addMessage(
        message: String,
        author: String = "user"
    ) {
        viewModelScope.launch {
            val msg = ChatMessage(
                text = message,
                author = Author(author),
                id = messages.size
            )
            _messages.add(msg)

            if(author == "user" && ifExpectingPrediction.value) {
                makePrediction(
                    question = message
                )
            }
        }
    }

    fun makePrediction(question: String) {
        viewModelScope.launch {
            val prediction = RetrofitClient.tarotReaderAPIService.getPrediction(
                PredictRequest(question)
            )
            addMessage(
                message = prediction.body()?.prediction ?: "Sorry, I don't know",
                author = "app"
            )
            ifExpectingPrediction.value = false
        }
    }

    fun clickChip(
        text: String
    ) {
        addMessage(text)
        showChips.value = false
    }
}

data class Author(
    val id: String,
)

@Immutable
data class ChatMessage(
    val text: String,
    val author: Author,
    val id: Int,
    val ifFullWidth: Boolean = false,
    val options: List<ChatMessageOption>? = null
) {
    val isFromMe: Boolean
        get() = author.id != "app"
}

data class ChatMessageOption(
    val onClick: () -> Unit,
    val content: @Composable () -> Unit,
)

data class TarotReader(
    val name: String,
    @DrawableRes val avatar: Int,
    val shortDescription: String,
    val isOnline: Boolean,
)

data class Prediction (
    val question: String,
    val prediction: String,
    val prediction_id: String
)