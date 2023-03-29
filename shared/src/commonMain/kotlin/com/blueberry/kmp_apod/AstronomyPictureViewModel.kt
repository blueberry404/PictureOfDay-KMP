package com.blueberry.kmp_apod

import com.blueberry.kmp_apod.data.AstronomyPicture
import com.blueberry.kmp_apod.data.AstronomyPictureRepository
import com.blueberry.kmp_apod.data.RemoteAstronomyPicture
import com.blueberry.kmp_apod.dates.AstronomyDate
import com.blueberry.kmp_apod.utils.PlatformUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

class AstronomyPictureViewModel {
    var pictureInfo = MutableStateFlow(AstronomyPictureState(isLoading = true))
        private set

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val repository = AstronomyPictureRepository()
    private var dates: MutableList<AstronomyDate> = mutableListOf()
    private val platformUtil = PlatformUtil()

    init {
        val currentDate = Clock.System.now().toString()
        val formattedDate = platformUtil.getAPIFormattedDate(currentDate)
        pictureInfo.update { AstronomyPictureState(isLoading = true, selectedDate = currentDate) }
        calculateDates()
        getPictureInfo(formattedDate)
    }

    private fun getPictureInfo(selectedDate: String) {
        scope.launch {
            val response = repository.getPictureOfDay(selectedDate)
            pictureInfo.update {
                it.copy(
                    astronomyPicture = getFormattedObject(response),
                    isLoading = false,
                    showDates = false,
                    dates = dates
                )
            }
        }
    }

    private fun calculateDates() {
        val now = Clock.System.now()

        for (i in 0 until 5) {
            val date = now.minus(i.days).toLocalDateTime(TimeZone.currentSystemDefault())
            val astronomyDate = AstronomyDate(
                month = date.month.name.take(3),
                date = date.dayOfMonth.toString(),
                day = date.dayOfWeek.name.take(3),
                isSelected = i == 0,
                dateInfo = date.date
            )
            dates.add(astronomyDate)
        }
        dates.reverse()
    }

    fun selectDate(astronomyDate: AstronomyDate) {
        pictureInfo.update {
            it.copy(showDates = false, isLoading = true)
        }
        val dateInfo = astronomyDate.dateInfo.toString()
        getPictureInfo(dateInfo)
    }

    fun toggleDatesVisibility(showDates: Boolean) {
        pictureInfo.update { it.copy(showDates = showDates) }
    }

    private fun getFormattedObject(remoteAstronomyPicture: RemoteAstronomyPicture): AstronomyPicture {
        remoteAstronomyPicture.apply {
            return AstronomyPicture(
                platformUtil.getFormattedDate(date, FORMAT_API, FORMAT_DISPLAY),
                explanation, mediaType, title, url)
        }
    }

    companion object {
        const val FORMAT_API = "yyyy-MM-dd"
        const val FORMAT_DISPLAY = "dd MMMM yyyy"
    }
}

data class AstronomyPictureState(
    val astronomyPicture: AstronomyPicture? = null,
    val isLoading: Boolean = false,
    val selectedDate: String? = null,
    val showDates: Boolean = false,
    val dates: List<AstronomyDate> = emptyList(),
)
