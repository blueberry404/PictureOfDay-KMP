package com.blueberry.kmp_apod

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blueberry.kmp_apod.data.AstronomyPicture
import com.blueberry.kmp_apod.dates.DatesWidget
import com.seiko.imageloader.rememberAsyncImagePainter
import kotlinx.coroutines.delay


@Composable
internal fun PictureOfDayScreen(
    modifier: Modifier = Modifier,
    vm: AstronomyPictureViewModel
) {
    val state = vm.pictureInfo.collectAsState()
    var isDatesVisible by remember { mutableStateOf(false) }//mutableStateOf(vm.pictureInfo.value.showDates) }

    LaunchedEffect(Unit) {
        delay(3000L)
        isDatesVisible = true
    }

    state.value.astronomyPicture?.let {
        Column(modifier.background(Color.Black)) {
            ApodContent(modifier = modifier.fillMaxWidth().weight(1f), astronomyPicture = it)
            AnimatedVisibility(visible = isDatesVisible,
                enter = slideInVertically(
                    animationSpec = tween(400),
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    animationSpec = tween(400),
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ) {
                DatesWidget(
                    modifier = modifier,
                    state.value.dates
                ) {
                    isDatesVisible = false
                }
            }
        }
    }
}

@Composable
internal fun ApodContent(modifier: Modifier = Modifier, astronomyPicture: AstronomyPicture) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .scrollable(scrollState, Orientation.Vertical),
    ) {
        val painter = rememberAsyncImagePainter(astronomyPicture.url.orEmpty())
        Text(
            text = "Picture of the Day",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painter,
            contentDescription = "Picture of Day",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = astronomyPicture.title,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = astronomyPicture.date,
            fontSize = 14.sp,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = astronomyPicture.explanation,
            fontSize = 14.sp,
            color = Color.White,
        )
    }
}