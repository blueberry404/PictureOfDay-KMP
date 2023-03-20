package com.blueberry.kmp_apod

import com.blueberry.kmp_apod.data.AstronomyPicture
import com.blueberry.kmp_apod.data.RemoteAstronomyPicture
import com.blueberry.kmp_apod.data.AstronomyPictureRepository
import com.blueberry.kmp_apod.dates.AstronomyDate
import com.soywiz.klock.DateFormat
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
    private var dates: List<AstronomyDate> = emptyList()
    private val dateFormat: DateFormat = DateFormat("dd MMM yyyy")

    init {
        val currentDate = DateTime.now().format(ISO8601.DATE_CALENDAR_COMPLETE)
        pictureInfo.update { AstronomyPictureState(isLoading = true, selectedDate = currentDate) }
        calculateDates()
        getPictureInfo(currentDate)
    }

    private fun getPictureInfo(selectedDate: String) {
        scope.launch {
            val response = repository.getPictureOfDay(selectedDate)
            pictureInfo.update {
                it.copy(
                    astronomyPicture = getObject(response),
                    isLoading = false,
                    showDates = false,
                    dates = dates
                )
            }
        }
    }

    private fun calculateDates() {
        dates = listOf(
            AstronomyDate("Mar", "14", "2023", "Tues", false),
            AstronomyDate("Mar", "15", "2023", "Wed", false),
            AstronomyDate("Mar", "16", "2023", "Thus", false),
            AstronomyDate("Mar", "17", "2023", "Fri", false),
            AstronomyDate("Mar", "18", "2023", "Sat", true),
        )
    }

    fun selectDate(astronomyDate: AstronomyDate) {
        pictureInfo.update {
            it.copy(showDates = false, isLoading = true)
        }
        getPictureInfo("${astronomyDate.year}-03-${astronomyDate.date}")
    }

    fun toggleDatesVisibility(showDates: Boolean) {
        pictureInfo.update { it.copy(showDates = showDates) }
    }

    fun getObject(remoteAstronomyPicture: RemoteAstronomyPicture): AstronomyPicture {
        remoteAstronomyPicture.apply {
            return AstronomyPicture(date, explanation, mediaType, title, url)
        }
    }
}

data class AstronomyPictureState(
    val astronomyPicture: AstronomyPicture? = null,
    val isLoading: Boolean = false,
    val selectedDate: String? = null,
    val showDates: Boolean = false,
    val dates: List<AstronomyDate> = emptyList(),
)
