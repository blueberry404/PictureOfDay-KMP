package com.blueberry.kmp_apod.data

class AstronomyPictureRepository  {
    private val pictureService = AstronomyPictureService()

    suspend fun getPictureOfDay(date: String): AstronomyPicture =
        pictureService.getPictureOfDay(date)
}