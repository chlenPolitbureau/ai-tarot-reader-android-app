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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.Chat
import com.tarotreader.app.R
import com.tarotreader.app.ui.theme.Typography
import com.tarotreader.app.ui.theme.bkgrndGrey
import com.tarotreader.app.ui.theme.poshGreen


class DailyAdvice (
    @DrawableRes val img: Int,
    val header: String,
    val textColor: Color = Color.White,
    val bkgrndColor: Color = Color(0x00FFFFFF),
    val route: kotlin.Any
) {
    lateinit var navController: NavHostController

    fun navigate() {
        navController.navigate(route)
    }
}

@Composable
fun DailyAdviceCard(
    dailyAdvice: DailyAdvice,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clickable { dailyAdvice.navigate() }
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

val adviceOne = DailyAdvice(
    img = R.drawable.rewievpredictionsthumbnail,
    header = "Review past readings",
    bkgrndColor = bkgrndGrey,
    route = Chat
)

@Preview
@Composable
fun DailyAdviceCardPreview() {
    DailyAdviceCard(
        dailyAdvice = adviceOne,
        modifier = Modifier
    )
}