package com.tarotreader.app.model

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.Chat

import com.tarotreader.app.Content

import com.tarotreader.app.ui.theme.Typography

import com.tarotreader.app.ui.theme.poshGreen
import kotlinx.coroutines.launch


class DailyAdvice (
    @DrawableRes val img: Int,
    val header: String,
    val textColor: Color = Color.White,
    val bkgrndColor: Color = Color(0x00FFFFFF),
    val route: kotlin.Any
)

@Composable
fun DailyAdviceCard(
    dailyAdvice: DailyAdvice,
    navController: NavHostController,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clickable { navController.navigate(
                    dailyAdvice.route
                ) }
                .border(width = 3.dp, color = poshGreen, shape = RoundedCornerShape(10.dp))
                .height(150.dp)
                .width(130.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = dailyAdvice.img),
                contentDescription = dailyAdvice.header,
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier.background(dailyAdvice.bkgrndColor)
            ) {
                Text(
                    text = dailyAdvice.header,
                    style = Typography.titleMedium,
                    color = dailyAdvice.textColor,
                    modifier = Modifier.padding(
                        start = 15.dp,
                        top = 15.dp
                    )
                )
            }
        }
    }
}