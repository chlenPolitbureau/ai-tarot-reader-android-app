package com.tarotreader.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarotreader.app.R
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotReader

@Composable
fun TarotReaderCard(
    reader: TarotReader,
    chatViewModel: ChatViewModel
) {
    val spreadSelected by chatViewModel.predictionSpread

    Card (
        modifier = Modifier.height(150.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                // 1. Avatar Column
                Image(
                    painter = painterResource(R.drawable.frog_mascot),
                    contentDescription = "Avatar of ${reader.name}",
                    modifier = Modifier
                        .width(150.dp)
                        .fillMaxHeight()
                )

                Spacer(modifier = Modifier.width(16.dp))

                // 2. Name and Description Column
                Column (
                    modifier = Modifier.padding(top=15.dp)
                ) {
                    Text(
                        text = reader.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = reader.shortDescription,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Online Indicator
                    Text(
                        text = "Spread: ${if(spreadSelected != null) 
                            spreadSelected?.toReadableString() else "not selected"}"
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun TarotReaderCardPreview() {
//    TarotReaderCard(
//        reader = TarotReader(
//            name = "Lazy Bones",
//            avatar = R.drawable.avatar_lazybones_sloth_svgrepo_com,
//            shortDescription = "I'm a Tarot Reader!",
//            isOnline = true
//        ),
//        chatViewModel =
//    )
//}