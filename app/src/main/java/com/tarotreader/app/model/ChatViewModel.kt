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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarotreader.app.data.ChatDataSource
import com.tarotreader.app.data.PredictRequest
import com.tarotreader.app.data.RetrofitClient
import com.tarotreader.app.ui.ShuffleDeck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor (
    private val appViewModel: AppViewModel
): ViewModel() {
    private val _messages = mutableStateListOf(ChatDataSource.initState)
    private val _listOfCardStates = mutableStateListOf<MutableList<Boolean>>()
    val messages = _messages
    val listOfCardStates = _listOfCardStates

    val ifExpectingPrediction: MutableState<Boolean> = mutableStateOf(false)
    val question: MutableState<String> = mutableStateOf("")
    val showChips: MutableState<Boolean> = mutableStateOf(true)
    val showController: MutableState<Boolean> = mutableStateOf(true)
    val spreadSelected: MutableState<Boolean> = mutableStateOf(false)
    val predictionSpread: MutableState<Spread> = mutableStateOf(Spread.THREE_CARDS)
    var predictionListOfCards: MutableState<List<TarotCard>> = mutableStateOf(listOf())

    suspend fun savePredictionToDataStore(prediction: Prediction) {
        appViewModel.writePrediction(prediction)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMessage(
        chatMessage: ChatMessage,
    ) {
        viewModelScope.launch {
            if (chatMessage.author.id == "user") {
                _messages.add(chatMessage)
                manageMessage(chatMessage)
                question.value = chatMessage.text
                showController.value = !showController.value
                _listOfCardStates.add(mutableListOf(false))
            } else if (chatMessage.author.id == "app") {
                _messages.add(chatMessage)
                if (chatMessage.draw != null) {
                    _listOfCardStates.add(
                        chatMessage.draw.cardsFlipState
                    )
                } else {
                    _listOfCardStates.add(
                        mutableListOf(false)
                    )
                }
            } else {
            }
        }
    }

    fun flipCard(
        nMessage: Int,
        index: Int
    ) {
        _listOfCardStates[nMessage][index] = !_listOfCardStates[nMessage][index]
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
            val cardsDrawn = spread.draw()
            val draw = Draw(
                spread = spread,
                listOfCards = cardsDrawn,
                postback = {
                    makePrediction()
                }
            )

            updateSelectedCards(cardsDrawn)
            val msg = ChatMessage(
                text = "Tap the cards to turn them over and make a prediction",
                author = Author("app"),
                ifFullWidth = true,
                draw = draw
            )
            addMessage(msg)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makePrediction() {
        viewModelScope.launch {
            val cardsString = predictionListOfCards.value.joinToString(":") {
                it.name + "," + it.orientation.toString()
            }
            val prediction = RetrofitClient.tarotReaderAPIService.getPrediction(
                PredictRequest(
                    q=question.value,
                    cardsPicked = cardsString,
                    spread = predictionSpread.value.name
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
                question = question.value,
                prediction = response,
                dateTime = LocalDateTime.now().toString(),
                spread = predictionSpread.value,
                cards = predictionListOfCards.value
            )
            savePredictionToDataStore(pred) // write prediction locally into DataStore
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

    init {
        _listOfCardStates.add(
            mutableListOf(false)
        )
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
    val draw: Draw? = null
) {
    val isFromMe: Boolean
        get() = author.id != "app"
}

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
    val spread: Spread,
    val cards: List<TarotCard>
)