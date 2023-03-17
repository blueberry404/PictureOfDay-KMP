package com.blueberry.kmp_apod

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blueberry.kmp_apod.data.AstronomyPicture
import com.seiko.imageloader.rememberAsyncImagePainter


@Composable
internal fun PictureOfDayScreen(
    modifier: Modifier = Modifier,
    vm: AstronomyPictureViewModel
) {
    val state = vm.pictureInfo.collectAsState()
    state.value.astronomyPicture?.let {
        ApodContent(modifier = modifier, astronomyPicture = it)
    }
}

@Composable
internal fun ApodContent(modifier: Modifier = Modifier, astronomyPicture: AstronomyPicture) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .background(Color.Black)
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