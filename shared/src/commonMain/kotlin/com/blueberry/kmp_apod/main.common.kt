package com.blueberry.kmp_apod

import androidx.compose.runtime.Composable

@Composable
internal fun MainCommon() {
    val viewModel = AstronomyPictureViewModel()
    MyApplicationTheme {
        PictureOfDayScreen(vm = viewModel)
    }
}