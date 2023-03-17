package com.blueberry.kmp_apod

import com.blueberry.kmp_apod.data.AstronomyPicture
import com.blueberry.kmp_apod.data.AstronomyPictureRepository
import com.soywiz.klock.DateTime
import com.soywiz.klock.ISO8601
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AstronomyPictureViewModel {
    var pictureInfo = MutableStateFlow(AstronomyPictureState(isLoading = true))
        private set

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val repository = AstronomyPictureRepository()

    init {
        val currentDate = DateTime.now().format(ISO8601.DATE_CALENDAR_COMPLETE)
        pictureInfo.update { AstronomyPictureState(isLoading = true, selectedDate = currentDate) }
        getPictureInfo(currentDate)
    }

    fun getPictureInfo(selectedDate: String) {
        scope.launch {
            val response = repository.getPictureOfDay(selectedDate)
            pictureInfo.update { it.copy(astronomyPicture = response, isLoading = false) }
        }
    }
}

data class AstronomyPictureState(
    val astronomyPicture: AstronomyPicture? = null,
    val isLoading: Boolean = false,
    val selectedDate: String? = null,
)
