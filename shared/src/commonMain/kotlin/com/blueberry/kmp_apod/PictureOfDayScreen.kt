package com.blueberry.kmp_apod

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.blueberry.kmp_apod.data.AstronomyPicture
import com.blueberry.kmp_apod.dates.DatesWidget
import com.blueberry.kmp_apod.progressindicator.SemiCircleSpinProgressIndicator
import com.seiko.imageloader.rememberAsyncImagePainter


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun PictureOfDayScreen(
    modifier: Modifier = Modifier,
    vm: AstronomyPictureViewModel
) {
    val state = vm.pictureInfo.collectAsState()
    val isDatesVisible = vm.pictureInfo.value.showDates

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if(state.value.isLoading) {
            Column(modifier = Modifier.fillMaxSize().zIndex(2.0f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                SemiCircleSpinProgressIndicator(color = Color.Yellow)
            }
        }
        state.value.astronomyPicture?.let {
            Column(modifier.background(Color.Black)) {
                ApodContent(modifier = modifier.fillMaxWidth().weight(1f), astronomyPicture = it)
                AnimatedVisibility(
                    visible = !isDatesVisible,
                    enter = scaleIn(),
                    exit = scaleOut(),
                ) {
                    FooterNote { vm.toggleDatesVisibility(true) }
                }
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
                        vm.selectDate(it)
                    }
                }
            }
        }
    }
}

@Composable
internal fun FooterNote(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Interested to check on more dates? ",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                modifier = Modifier.height(35.dp),
                onClick = onClick,
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Constants.getTealColor(),
                    contentColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = "Click here",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
internal fun ApodContent(modifier: Modifier = Modifier, astronomyPicture: AstronomyPicture) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.padding(8.dp)
            .scrollable(scrollState, Orientation.Vertical),
    ) {
        val painter = rememberAsyncImagePainter(astronomyPicture.url.orEmpty())
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Picture of the Day",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (astronomyPicture.mediaType == "image") {
            Image(
                painter = painter,
                contentDescription = "Picture of Day",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            )
        }
        else {
            val uriHandler = LocalUriHandler.current
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Button(
                        modifier = Modifier.height(35.dp),
                        onClick = {
                            astronomyPicture.url?.let { uriHandler.openUri(it) }
                        },
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Constants.getTealColor(),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            modifier = Modifier.fillMaxHeight(),
                            text = "Play Video",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = astronomyPicture.title,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = astronomyPicture.date,
            style = TextStyle(letterSpacing = 2.sp),
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