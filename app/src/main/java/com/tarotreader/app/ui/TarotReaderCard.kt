package com.tarotreader.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarotreader.app.R
import com.tarotreader.app.model.TarotReader

@Composable
fun TarotReaderCard(reader: TarotReader) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 1. Avatar Column
            Image(
                painter = painterResource(R.drawable.avatar_lazybones_sloth_svgrepo_com),
                contentDescription = "Avatar of ${reader.name}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 2. Name and Description Column
            Column {
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
                if (reader.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TarotReaderCardPreview() {
    TarotReaderCard(
        reader = TarotReader(
            name = "Lazy Bones",
            avatar = R.drawable.avatar_lazybones_sloth_svgrepo_com,
            shortDescription = "I'm a Tarot Reader!",
            isOnline = true
        )
    )
}