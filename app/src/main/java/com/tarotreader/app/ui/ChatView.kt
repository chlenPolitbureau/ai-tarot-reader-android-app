package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.tarotreader.app.R
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.Author
import com.tarotreader.app.model.ChatMessage
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.model.CurrencyType
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotReader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.platform.LocalContext
import com.tarotreader.app.ui.theme.poshGreen

@Composable
fun ChatView(
    title: String = "Reading",
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    appViewModel: AppViewModel,
    chatViewModel: ChatViewModel,
    spreadParameter: Spread? = null
) {
    LaunchedEffect(key1 = true) {
        if (spreadParameter != null) {
            chatViewModel.updateSelectedSpread(spreadParameter)
        }
    }

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        TarotReaderCard(
            reader = TarotReader(
                name = "Lazy Bones",
                avatar = R.drawable.avatar_lazybones_sloth_svgrepo_com,
                shortDescription = "I'm a Tarot Reader!",
                isOnline = true
            ),
            chatViewModel = chatViewModel
        )
        EndFirstLazyColumn(
            modifier = Modifier.weight(1f),
            chatViewModel = chatViewModel
        )
        ChatController(
            chatViewModel = chatViewModel,
            appViewModel = appViewModel,
            modifier = Modifier.weight(1f),
        )
    }

    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                chatViewModel.updateSelectedSpread(null)
            } else if (event == Lifecycle.Event.ON_STOP) {

            }
        }
        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)
        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun ChatController(
    chatViewModel: ChatViewModel,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier,
) {
    val appDataStore = appViewModel.uiState.collectAsState()
    val userManaBalance by remember { mutableStateOf(appDataStore.value.currencies.first().amount ) }
    val currentSpread = chatViewModel.predictionSpread
    val scope = rememberCoroutineScope()

    Column (
    ) {
        if (chatViewModel.showController.value) {

            if (
                chatViewModel.ifExpectingPrediction.value &&
                !chatViewModel.spreadSelected
                ) {
                ChooseSpread(
                    chatViewModel = chatViewModel,
                )

                // filter spread that available by mana points
            }
            else if (
                chatViewModel.ifExpectingPrediction.value &&
                chatViewModel.spreadSelected &&
                userManaBalance >= currentSpread.value?.manaCost ?: 5
                    ) {
                ChatInput(
                    onMessageSend = {
                        scope.launch {
                            appViewModel.updateCurrency(
                                type = CurrencyType.MANA,
                                amount = (currentSpread.value?.manaCost?.toLong() ?: 5) * -1
                            )
                            chatViewModel.addMessage(
                                ChatMessage(
                                    text = it,
                                    author = Author("user")
                                )
                            )
                        }
                    }
                )
                if (chatViewModel.showChips.value) {
                    SuggestedQuestions(
                        questionList = listOf(
                            "Will I succeed in my career?",
                            "What's the weather will be tomorrow?",
                            "Should I go to the gym today?"
                        ),
                        chatViewModel = chatViewModel
                    )
                }
            } else if (
                chatViewModel.ifExpectingPrediction.value
                && chatViewModel.spreadSelected
                && userManaBalance < currentSpread.value?.manaCost ?: 5
                    ) {
                // show message that not enough mana points and a choose spread button
                Row {
                    ElevatedButton(onClick = {
                        scope.launch {
                            chatViewModel.spreadSelected = false
                        }
                    }) {
                        Text("Back to Spread picker")
                    }

                    ElevatedButton(onClick = {
                        // invoke ads and add credits
                    }) {
                        Text("Watch ads to get credits")
                    }
                }

            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    ElevatedButton(
                        modifier = modifier,
                        onClick = {
                            chatViewModel.ifExpectingPrediction.value =
                                !chatViewModel.ifExpectingPrediction.value
                        }
                    ) { Text("Next prediction") }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChooseSpread(
    chatViewModel: ChatViewModel,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp)
) {
    val spreads = Spread.entries

    Column(
        modifier = modifier
    ) {
        Text(
            "Choose spread",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )
        Row (
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButton(
                modifier = Modifier.defaultMinSize(
                    minWidth = 160.dp
                ) ,
                onClick = { chatViewModel.updateSelectedSpread(
                    spreads[0]
                ) }
            ) {
                Text(spreads[0].shortDescription)
            }
            ElevatedButton(
                modifier = Modifier.defaultMinSize(
                    minWidth = 160.dp
                ),
                onClick = { chatViewModel.updateSelectedSpread(
                    spreads[1]
                ) }
            ) {
                Text(spreads[1].shortDescription)
            }
        }
        Row (
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButton(
                onClick = { chatViewModel.updateSelectedSpread(
                    spreads[2]
                ) }
            ) {
                Text(spreads[2].shortDescription)
            }
            ElevatedButton(
                onClick = { chatViewModel.updateSelectedSpread(
                    spreads[3]
                ) }
            ) {
                Text(spreads[3].shortDescription)
            }
            ElevatedButton(
                onClick = { chatViewModel.updateSelectedSpread(
                    spreads[4]
                ) }
            ) {
                Text(spreads[4].shortDescription)
            }
        }
    }
}

@Composable
fun SuggestedQuestions(
    questionList: List<String>,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(start = 6.dp)
    ) {
        items(questionList.size) {
            index ->
            FilterChipSample(
                text = questionList[index],
                chatViewModel = chatViewModel
            )
        }
    }
}

@Preview
@Composable
fun FilterChipSample(
    text: String,
    chatViewModel: ChatViewModel
) {
    var selected by remember { mutableStateOf(false) }
    FilterChip(
        selected = selected,
        onClick = { chatViewModel.clickChip(text)
                    chatViewModel.ifExpectingPrediction.value =
                        !chatViewModel.ifExpectingPrediction.value
                  },
        label = { Text(text) },
        leadingIcon =
        if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}

@Composable
fun EndFirstLazyColumn(
    modifier: Modifier,
    chatViewModel: ChatViewModel
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = listState,
        modifier = modifier
            .padding(top = 8.dp)
    ) {
        items(chatViewModel.messages.size) { index ->
            if (chatViewModel.messages[index].draw != null) {
                Message(message = chatViewModel.messages[index])
                DrawMessage(
                    n = index,
                    chatViewModel = chatViewModel,
                    postback = { chatViewModel.makePrediction() }
                )
            } else {
                Message(message = chatViewModel.messages[index])
            }
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            listState.scrollToItem(listState.layoutInfo.totalItemsCount - 1)
        }
    }

    SideEffect {
        coroutineScope.launch {
            listState.scrollToItem(listState.layoutInfo.totalItemsCount - 1)
        }
    }
}

@Composable
fun ChatInput(
    onMessageSend: (String) -> Unit,
) {
    var messageText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = messageText,
            keyboardOptions = KeyboardOptions.Default,
            onValueChange = { messageText = it },
            modifier = Modifier.weight(1f),
            maxLines = 2,
            placeholder = { Text("Type your question here...") },
            suffix = {
                if (messageText != "") {
                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                onMessageSend(messageText)
                            }
                        }
                    ) {
                       Icon(imageVector = Icons.Default.Star,
                           contentDescription = "")
                    }
                }
            }
        )
    }
}

@Composable
fun ChatItemBubble(
    message: ChatMessage
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isFromMe) Color.Blue else Color.LightGray,
                    shape = if (message.isFromMe) RoundedCornerShape(
                        bottomEnd = 16.dp,
                        topStart = 16.dp,
                        bottomStart = 16.dp
                    )
                    else RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .padding(12.dp)
                .widthIn(max = 270.dp)
        ) {
            if (!message.isFromMe && message.draw == null) {
                GifThenText(
                    gif = R.drawable.three_dots,
                    text = message.text,
                    color = Color.Black,
                    wasLoaded = message.ifLoaded
                )
            }
            else if (message.draw != null) {
                Text(
                    text = message.text,
                    color = Color.Black
                )
            }
            else {
                Text(
                    text = message.text,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DrawMessage(
    n: Int,
    chatViewModel: ChatViewModel,
    postback: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Card (
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(
                1.dp,
                color = poshGreen,
            )
        ) {
            Shuffle(
                n = n,
                chatViewModel = chatViewModel,
                postback = postback
            )
        }
    }
}

@Composable
fun Message(
    message: ChatMessage,
) {
    Column {
        ChatItemBubble(message = message)
        }
}

@Composable
fun TypeWriter(
    text: String,
    color: Color = Color.Black
) {
    var textToDisplay by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        for (i in 1 .. text.length) {
            textToDisplay = text.substring(0, i)
            delay(20)
        }
    }

    Text(text = textToDisplay, color = color)
}

@Preview
@Composable
fun ChatMessagePreview() {
    ChatItemBubble(
        message = ChatMessage(
            text = "Hello, worldspdjoijdfi jsad'ofkpoqkerf'poqekrogkeor!",
            author = Author("1")
            )
    )
}

@Composable
fun GifThenText(
    @DrawableRes gif: Int,
    text: String,
    color: Color,
    wasLoaded: Boolean,
    modifier: Modifier= Modifier
) {
    var showGif by remember { mutableStateOf(true) }
//    var hasGifBeenShown by remember { mutableStateOf(wasLoaded) }

    LaunchedEffect(key1 = true) {
        if (!wasLoaded) {
            val randomInterval = (500..2200).random().toLong()
            delay(randomInterval) // Wait for 2 seconds
            showGif = false // Change to text after 2 seconds}
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (showGif) {
            // Display the GIF
            val context = LocalContext.current
            val painter = rememberDrawablePainter(
                drawable = getDrawable(
                    context,
                    gif
                )
            )
            Image(
                painter = painter,
                contentDescription = "Loading animation",
            )
        } else {
            // Display the text
            Text(
                text = text,
                color = color
            )
        }
    }
}