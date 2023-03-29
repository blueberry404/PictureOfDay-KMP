package com.blueberry.kmp_apod.utils

import java.time.format.DateTimeFormatter

actual class PlatformUtil actual constructor() {
    actual fun getFormattedDate(
        date: String,
        currentFormat: String,
        expectedFormat: String
    ): String {
        val received = DateTimeFormatter.ofPattern(currentFormat).parse(date)
        return DateTimeFormatter.ofPattern(expectedFormat).format(received)
    }

    actual fun getAPIFormattedDate(date: String): String {
        val received = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").parse(date)
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(received)
    }
}