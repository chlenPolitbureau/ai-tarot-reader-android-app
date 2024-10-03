package com.tarotreader.app.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tarotreader.app.R
import com.tarotreader.app.model.Author
import com.tarotreader.app.model.ChatMessage
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.model.TarotReader
import kotlinx.coroutines.launch

@Composable
fun ChatView(
        chatViewModel: ChatViewModel
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
            messages = chatViewModel.messages,
            modifier = Modifier.weight(1f)
        )
//        ChatInput(
//            onMessageSend = { chatViewModel.addMessage(it) } ,
//            modifier = Modifier.weight(1f))
        ChatController(
            chatViewModel = chatViewModel,
            modifier = Modifier.weight(1f))
    }
}

@Composable
fun ChatController(
    chatViewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    Column (
    ) {
        val message = mutableStateOf(chatViewModel.question.value)

        if (chatViewModel.ifExpectingPrediction.value) {
            ChatInput(
                onMessageSend = { chatViewModel.addMessage(it) },
                modifier = modifier,
                chatViewModel = chatViewModel
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
        } else {
            Row (
                horizontalArrangement = Arrangement.Center
            ){
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

@Composable
fun SuggestedQuestions(
    questionList: List<String>,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(4.dp)
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
        onClick = { chatViewModel.clickChip(text) },
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
}

@Composable
fun ChatInput(
    onMessageSend: (String) -> Unit,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel
) {
    var messageText by remember { mutableStateOf(chatViewModel.question.value) }

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
            placeholder = { Text("Type your question here...") },
            suffix = {
                if (messageText != "") {
                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                onMessageSend(messageText)
                                messageText = ""
                            }
                        }
                    ) {
                       Icon(imageVector = Icons.Default.Send,
                           contentDescription = "")
                    }
                }
            }
        )
    }
}

@Composable
fun ChatItemBubble(message: ChatMessage) {
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
                .widthIn(max = 250.dp)
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
        if (message.options != null && !message.ifFullWidth) {
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

@Preview
@Composable
fun ChatMessagePreview() {
    ChatItemBubble(
        message = ChatMessage(
            text = "Hello, worldspdjoijdfi jsad'ofkpoqkerf'poqekrogkeor!",
            author = Author("111111"),
            id = 0
            )
    )
}

@Preview
@Composable
fun ChatViewPreview() {
    ChatView(
        chatViewModel = ChatViewModel()
    )
}