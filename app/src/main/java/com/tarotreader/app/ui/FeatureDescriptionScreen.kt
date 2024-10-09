package com.tarotreader.app.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tarotreader.app.data.FeaturesDataSource
import com.tarotreader.app.model.Feature
import com.tarotreader.app.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureDescriptionScreen(
    featureList : List<Feature> = FeaturesDataSource.features,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState { featureList.count() }

    Column(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(featureList[0].backgroundImage),
                contentDescription = featureList[0].title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.33f
            )
            HorizontalPager(
                state = pagerState,
            ) { i ->
                val item = featureList[i]
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    FeatureCard(
                        feature = item
                    )
                }
            }
            PageIndicator(
                pageCount = featureList.size,
                currentPage = pagerState.currentPage,
                modifier = modifier
                    .padding(top = 50.dp)
                    .align(Alignment.Center)
            )
            Button(
                onClick = onNextButtonClicked,
                modifier = modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.BottomCenter)

            ) {
                Text(text = "Next")
            }
        }
    }
}
                                                                                                                
@Composable
fun                                  FeatureCard(
    feature: Feature,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 60.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = feature.title,
            style = Typography.titleLarge,
            modifier = modifier.padding(14.dp)
        )
        Text(
            text = feature.description,
            textAlign = TextAlign.Center,
            style = Typography.bodyMedium
        )
    }
}
/* Dot indicator code below */

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(modifier = modifier
        .padding(2.dp)
        .size(size.value)
        .clip(CircleShape)
        .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount){
            IndicatorDots(isSelected = it == currentPage, modifier= modifier)
        }
    }
}

@Preview
@Composable
fun FeatureDescriptionScreenPreview() {
    FeatureDescriptionScreen(
        onNextButtonClicked = {}
    )
}