package com.tarotreader.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tarotreader.app.data.ArticlesDataSource
import com.tarotreader.app.model.Article
import com.tarotreader.app.model.TarotCard

@Composable
fun ContentViewPage(
    type: String?,
    id: String?,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    when(type) {
        "card" -> CardDescriptionViewPage(cardName = id?: "")
    }
}

@Composable
fun ArticleViewPage() {

}

@Composable
fun CardDescriptionViewPage(
    cardName: String
) {
    val card = TarotCard.entries.find { it.name == cardName }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (card != null) {
            Image(painter = painterResource(id = card.img), contentDescription = "")
        }
        if (card != null) {
            Text(text = card.name)
        }
    }
}

@Composable
fun LayoutDescriptionViewPage() {

}