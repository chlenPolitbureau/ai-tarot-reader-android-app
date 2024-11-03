package com.tarotreader.app.model

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarotreader.app.AppSettings
import com.tarotreader.app.data.ChatDataSource
import com.tarotreader.app.data.PredictRequest
import com.tarotreader.app.data.RetrofitClient
import com.tarotreader.app.ui.ShuffleDeck
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


class AppViewModel(
    val dataStore: DataStore<AppSettings>,
): ViewModel() {
    private val _messages = mutableStateListOf(ChatDataSource.initState)
    val messages = _messages

    val ifExpectingPrediction: MutableState<Boolean> = mutableStateOf(false)
    val question: MutableState<String> = mutableStateOf("")
    val showChips: MutableState<Boolean> = mutableStateOf(true)
    val showController: MutableState<Boolean> = mutableStateOf(true)
    val spreadSelected: MutableState<Boolean> = mutableStateOf(false)
    val predictionSpread: MutableState<Spread> = mutableStateOf(Spread.THREE_CARDS)
    var predictionListOfCards: MutableState<List<TarotCard>> = mutableStateOf(listOf())

    val currentUserName = dataStore.data.map {
        appSettings -> appSettings.userName
    }

    val currentManaPoints = dataStore.data.map {
        appSettings -> appSettings.manaPoints
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMessage(
        chatMessage: ChatMessage
    ) {
        viewModelScope.launch {
            if (chatMessage.author.id == "user") {
                _messages.add(chatMessage)
                manageMessage(chatMessage)
                question.value = chatMessage.text
                showController.value = !showController.value
            } else if (chatMessage.author.id == "app") {
                _messages.add(chatMessage)
            } else {
            }
        }
    }

    fun saveOpenCards(message: ChatMessage, i: Int) {
        _messages.removeAt(i)
        _messages.add(message)
    }

    private fun drawCards(spread: Spread): List<TarotCard> {
        return Draw(spread).draw()
    }

    fun updateSelectedCards(cards: List<TarotCard>) {
        predictionListOfCards.value = cards
    }

    fun updateSelectedSpread(spread: Spread)  {
        predictionSpread.value = spread
        spreadSelected.value = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageMessage(
        message: ChatMessage,
    ) {
        if(message.author.id == "user" && ifExpectingPrediction.value) {
            val spread = predictionSpread.value
            val question = question.value
            val cardsDrawn = drawCards(spread)

            updateSelectedCards(cardsDrawn)
            val msg = ChatMessage(
                text = "Tap the cards to turn them over and make a prediction",
                author = Author("app"),
                ifFullWidth = true,
                options = listOf(
                    ChatMessageOption(
                        onClick = {},
                        content = { ShuffleDeck(
                            spread = spread,
                            cards = cardsDrawn,// FIX HERE - the cards are updatable
                            modifier = Modifier.padding(10.dp),
                            postback = {
                                makePrediction(
                                    question = question,
                                    cards = cardsDrawn,
                                    spread = spread
                                )
                            }
                        )}
                    )
                )
            )
            addMessage(msg)
        }
    }

    suspend fun updateMana(balance: Int, manaPoints: Int) {
        viewModelScope.launch {
            dataStore.updateData {
                it.copy(
                    manaPoints = balance + manaPoints
                )
            }
        }
    }

    fun updatePersonalSettings(
        name: String,
        gender: String,
        dateOfBirth: String
        ) {
        viewModelScope.launch {
            dataStore.updateData {
                it.copy(
                    userName = name,
                    gender = gender,
                    dateOfBirth = dateOfBirth
                )
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun makePrediction(
        question: String,
        cards: List<TarotCard>,
        spread: Spread
    ) {
        viewModelScope.launch {
            val cardsString = cards.joinToString(":") {
                it.name + "," + it.orientation.toString()
            }
            val prediction = RetrofitClient.tarotReaderAPIService.getPrediction(
                PredictRequest(
                    q=question,
                    cardsPicked = cardsString,
                    spread = spread.name
                )
            )
            val response = prediction.body()?.prediction ?: "Sorry, I can't help with that"
            addMessage(
                ChatMessage(
                    text = response,
                    author = Author("app")
                )
            )
            val pred = Prediction(
                question = question,
                prediction = response,
                dateTime = LocalDateTime.now().toString(),
                spread = spread
            )
            writePrediction(pred) // write prediction locally into DataStore
            ifExpectingPrediction.value = false
            showController.value = !showController.value
            spreadSelected.value = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun clickChip(
        text: String
    ) {
        addMessage(
            ChatMessage(
                text = text,
                author = Author("user")
            )
        )
        showChips.value = false
    }
}

data class Author(
    val id: String,
)

data class ChatMessage(
    val text: String,
    val author: Author,
    val ifFullWidth: Boolean = false,
    var showOptions: Boolean = true,
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
    val dateTime: String,
    val spread: Spread
)