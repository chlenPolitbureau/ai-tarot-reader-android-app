package com.tarotreader.app.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tarotreader.app.AppSettings
import com.tarotreader.app.data.ChatDataSource
import com.tarotreader.app.data.PredictRequest
import com.tarotreader.app.data.RetrofitClient
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


class ChatViewModel(
    val dataStore: DataStore<AppSettings>,
): ViewModel() {
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
                viewModelScope.launch {

                }
            }
        }
    }

    private suspend fun writePrediction(prediction: Prediction) {
        dataStore.updateData {
            it.copy(
                predictions = it.predictions.mutate {
                    it.add(prediction)
                }
            )
        }
    }

    fun makePrediction(question: String) {
        viewModelScope.launch {
            val prediction = RetrofitClient.tarotReaderAPIService.getPrediction(
                PredictRequest(question)
            )
            val response = prediction.body()?.prediction ?: "Sorry, I don't know"
            addMessage(
                message = response,
                author = "app"
            )
            val pred = Prediction(
                question = question,
                prediction = response
            )
            writePrediction(pred) // write prediction locally into DataStore
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

@Serializable
data class Prediction (
    val question: String,
    val prediction: String,
//    val prediction_id: String
)