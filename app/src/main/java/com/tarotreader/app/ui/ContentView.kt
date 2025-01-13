package com.tarotreader.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.R
import com.tarotreader.app.model.Spread
import com.tarotreader.app.model.TarotCard
import com.tarotreader.app.ui.theme.Typography

@Composable
fun ContentViewPage(
    title: String = "Content",
    type: String?,
    id: String?,
    postback: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    navController: NavHostController? = null
) {
    Column {
    when(type) {
        "card" -> {
            postback("Card")
            CardDescriptionViewPage(cardName = id ?: "", navController = navController!!)
        }
        "spread" -> {
            postback("Spread")
            LayoutDescriptionViewPage(spread = Spread.valueOf(id ?: ""))
        }
    }
    }
}

@Composable
fun ArticleViewPage() {

}

@Composable
fun CardDescriptionViewPage(
    cardName: String,
    navController: NavHostController
) {
    val card = TarotCard.entries.find { it.name == cardName }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (card != null) {
                Box(
                    modifier = Modifier
                        .height(260.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.background_horizontal),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        alpha = 0.33f
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = card.img),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(250.dp)
                                .padding(10.dp)
                        )
                    }
                }
                Text(
                    text = card.toString(),
                    style = Typography.titleSmall,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Dummy description goes here - it's usually a very long text with callbackFunc only, there is no complain, and callbackFunc.invoke() works as well. update, @crgarridos, you're right, the compiler does not" +
                            "Something doesn't add up; the error says you have a () -> Int, but you're actually using () -> Unit in the MCVE? Is that a typo @lannyf?",
                    style = Typography.bodyMedium,
                    modifier = Modifier.padding(10.dp)
                )
        }
    }
}

@Composable
fun LayoutDescriptionViewPage(
    spread: Spread
) {
    Box(
        modifier = Modifier
            .height(260.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_horizontal),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.33f
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = spread.schemeImg),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .padding(10.dp)
            )
        }
    }
    Text(
        text = spread.toString(),
        style = Typography.titleSmall,
        modifier = Modifier.padding(10.dp)
    )
}