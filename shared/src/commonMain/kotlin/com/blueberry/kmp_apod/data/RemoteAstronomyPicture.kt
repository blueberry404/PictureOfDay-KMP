package com.blueberry.kmp_apod.data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RemoteAstronomyPicture(
    val copyright: String?,
    val date: String,
    val explanation: String,
    val hdurl: String?,
    @SerialName("media_type") val mediaType: String,
    val title: String,
    val url: String?,
)

data class AstronomyPicture(
    val date: String,
    val explanation: String,
    val mediaType: String,
    val title: String,
    val url: String?,
)