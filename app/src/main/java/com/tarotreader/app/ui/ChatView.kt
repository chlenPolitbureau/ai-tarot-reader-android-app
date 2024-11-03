package com.tarotreader.app.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import com.tarotreader.app.R
import com.tarotreader.app.model.Author
import com.tarotreader.app.model.ChatMessage
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotReader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatView(
    title: String = "Reading",
    appViewModel: AppViewModel
) {

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        TarotReaderCard(
            reader = TarotReader(
                name = "Lazy Bones",
                avatar = R.drawable.avatar_lazybones_sloth_svgrepo_com,
                shortDescription = "I'm a Tarot Reader!",
                isOnline = true
            )
        )
        EndFirstLazyColumn(
            messages = appViewModel.messages,
            modifier = Modifier.weight(1f)
        )
        ChatController(
            appViewModel = appViewModel,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun ChatController(
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier,
) {
    val userManaBalance = appViewModel.currentManaPoints.collectAsState(initial = 30).value
    val currentSpread = appViewModel.predictionSpread
    val scope = rememberCoroutineScope()

    Column (
    ) {
        if (appViewModel.showController.value) {
            if (appViewModel.ifExpectingPrediction.value &&
                !appViewModel.spreadSelected.value) {
                ChooseSpread(
                    appViewModel = appViewModel,
                )
            }
            else if (appViewModel.ifExpectingPrediction.value &&
                appViewModel.spreadSelected.value) {
                ChatInput(
                    onMessageSend = {
                        scope.launch {
                            appViewModel.updateMana(
                                balance = userManaBalance,
                                manaPoints = currentSpread.value.manaCost * -1
                            )
                            appViewModel.addMessage(
                                ChatMessage(
                                    text = it,
                                    author = Author("user")
                                )
                            )
                        }
                    }
                )
                if (appViewModel.showChips.value) {
                    SuggestedQuestions(
                        questionList = listOf(
                            "Will I succeed in my career?",
                            "What's the weather will be tomorrow?",
                            "Should I go to the gym today?"
                        ),
                        appViewModel = appViewModel
                    )
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    ElevatedButton(
                        modifier = modifier,
                        onClick = {
                            appViewModel.ifExpectingPrediction.value =
                                !appViewModel.ifExpectingPrediction.value
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
    appViewModel: AppViewModel,
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
                onClick = { appViewModel.updateSelectedSpread(
                    spreads[0]
                ) }
            ) {
                Text(spreads[0].description)
            }
            ElevatedButton(
                modifier = Modifier.defaultMinSize(
                    minWidth = 160.dp
                ),
                onClick = { appViewModel.updateSelectedSpread(
                    spreads[1]
                ) }
            ) {
                Text(spreads[1].description)
            }
        }
        Row (
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButton(
                onClick = { appViewModel.updateSelectedSpread(
                    spreads[2]
                ) }
            ) {
                Text(spreads[2].description)
            }
            ElevatedButton(
                onClick = { appViewModel.updateSelectedSpread(
                    spreads[3]
                ) }
            ) {
                Text(spreads[3].description)
            }
            ElevatedButton(
                onClick = { appViewModel.updateSelectedSpread(
                    spreads[4]
                ) }
            ) {
                Text(spreads[4].description)
            }
        }
    }
}

@Composable
fun SuggestedQuestions(
    questionList: List<String>,
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel
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
                appViewModel = appViewModel
            )
        }
    }
}

@Preview
@Composable
fun FilterChipSample(
    text: String,
    appViewModel: AppViewModel
) {
    var selected by remember { mutableStateOf(false) }
    FilterChip(
        selected = selected,
        onClick = { appViewModel.clickChip(text)
                    appViewModel.ifExpectingPrediction.value =
                        !appViewModel.ifExpectingPrediction.value
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
    messages: List<ChatMessage>,
    modifier: Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = listState,
        modifier = modifier
            .padding(top = 8.dp)
    ) {
        items(messages.size) { index ->
            Message(message = messages[index])
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
                Text(
                    text = message.text,
                    color = if (message.isFromMe) Color.White else Color.Black
                )
        }
    }
}

@Composable
fun Message(
    message: ChatMessage,
) {

    val showOptions by remember { mutableStateOf(message.showOptions) }

    Column {
        ChatItemBubble(message = message)
        if (message.ifFullWidth) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Card {
                    message.options?.get(0)?.content?.invoke()
                }
            }
        }
        if (message.options != null && !message.ifFullWidth && showOptions) {
            LazyRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(message.options.size) { index ->
                    Card (
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Gray,
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            message.options[index].content()
                        }
                    }
                }
            }
        }
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