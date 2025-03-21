package com.tarotreader.app.model

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarotreader.app.data.ChatDataSource
import com.tarotreader.app.data.PredictRequest
import com.tarotreader.app.data.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor (
    private val appViewModel: AppViewModel,
    initialSpread: Spread? = null
): ViewModel() {
    val predictionSpread: MutableState<Spread?> = mutableStateOf(initialSpread)
    private var _messages = mutableStateListOf<ChatMessage>()
    private val _listOfCardStates = mutableStateListOf<MutableList<Boolean>>()
    private val _listOfIfShowns = mutableStateListOf<Boolean>()
    val messages = _messages
    val listOfCardStates = _listOfCardStates
    val listOfIfShowns = _listOfIfShowns
    val ifExpectingPrediction: MutableState<Boolean> = mutableStateOf(false)
    val question: MutableState<String> = mutableStateOf("")
    val showChips: MutableState<Boolean> = mutableStateOf(true)
    val showController: MutableState<Boolean> = mutableStateOf(true)
    var predictionListOfCards: MutableState<List<TarotCard>> = mutableStateOf(listOf())
    var spreadSelected: Boolean = false
        get() {
            return predictionSpread.value != null
        }
        set(value) {
            field = value
        }

    suspend fun savePredictionToDataStore(prediction: Prediction) {
        appViewModel.writePrediction(prediction)
    }

    fun updateMessage(index: Int) {
        _listOfIfShowns[index] = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMessage(
        chatMessage: ChatMessage,
    ) {
        viewModelScope.launch {
            if (chatMessage.author.id == "user") {
//                _messages.add(chatMessage)
                addMessage(chatMessage, true)
                manageMessage(chatMessage)
                question.value = chatMessage.text
                showController.value = !showController.value
                _listOfCardStates.add(mutableListOf(false))
            } else if (chatMessage.author.id == "app") {
                val index = _messages.size
//                _messages.add(chatMessage)
                addMessage(chatMessage, false)
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

    private fun addMessage(message: ChatMessage, ifShown: Boolean) {
        _messages.add(message)
        _listOfIfShowns.add(ifShown)
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

    fun updateSelectedSpread(spread: Spread?)  {
        predictionSpread.value = spread
        spreadSelected = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageMessage(
        message: ChatMessage,
    ) {
        if(message.author.id == "user" && ifExpectingPrediction.value) {
            val spread = predictionSpread.value ?: Spread.THREE_CARDS
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
                    spread = predictionSpread.value?.name ?: Spread.THREE_CARDS.name
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
                dateTime = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(),
                spread = predictionSpread.value?:Spread.THREE_CARDS,
                cards = predictionListOfCards.value
            )
            appViewModel.updateCurrency(
                type = CurrencyType.MANA,
                amount = predictionSpread.value?.manaCost?.toLong()?.times(-1) ?: -5L
            )
            savePredictionToDataStore(pred) // write prediction locally into DataStore
            ifExpectingPrediction.value = false
            showController.value = !showController.value
            spreadSelected = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun clickChip(
        text: String
    ) {
        addMessage(
            ChatMessage(
                text = text,
                author = Author("user"),
                ifLoaded = true
            )
        )
        showChips.value = false
    }

    init {
        _listOfCardStates.add(
            mutableListOf(false)
        )
        if (predictionSpread.value != null) {
//            _messages.add(ChatDataSource.spreadMessage)
            addMessage(ChatDataSource.spreadMessage, false)
        } else {
//            _messages.add(ChatDataSource.initState)
            addMessage(ChatDataSource.initState, false)
        }
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
    val draw: Draw? = null,
    var ifLoaded: Boolean = false
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
    val dateTime: Long,
    val spread: Spread,
    val cards: List<TarotCard>
)