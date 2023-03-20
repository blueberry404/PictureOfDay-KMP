package com.blueberry.kmp_apod.utils

import kotlinx.datetime.internal.JSJoda.DateTimeFormatter

actual class PlatformUtil actual constructor() {
    actual fun getFormattedDate(
        date: String,
        currentFormat: String,
        expectedFormat: String
    ): String {
        val received = DateTimeFormatter.ofPattern(currentFormat).parse(date)
        return DateTimeFormatter.ofPattern(expectedFormat).format(received)
    }
}